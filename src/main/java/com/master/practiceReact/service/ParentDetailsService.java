package com.master.practiceReact.service;

import com.master.practiceReact.Repository.ParentRepository;
import com.master.practiceReact.models.Entity.Parent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentDetailsService implements UserDetailsService {

    private final ParentRepository parentRepo;
    private final Logger logger = LoggerFactory.getLogger(ParentDetailsService.class);
    public ParentDetailsService(ParentRepository parentRepo) {
        this.parentRepo = parentRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Parent parent = (Parent) parentRepo.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("Parent not found"));

        // Load permissions
        List<SimpleGrantedAuthority> authorities = parent.getParentPermissions().stream()
                .map(pp -> new SimpleGrantedAuthority(pp.getPermission().getName()))
                .toList();

        return new org.springframework.security.core.userdetails.User(
                parent.getLoginId(),
                parent.getPassword(),
                authorities
        );
    }


    public Parent findById(Long kidId) {
        Parent parent = parentRepo.findById(kidId).orElseThrow(() -> new RuntimeException("Parent with provide Id not found"));
        return  parent;
    }

    public Parent getAuthenticatedParent(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated parent found");
        }

        Object principal = authentication.getPrincipal();

        String loginId;

        if (principal instanceof UserDetails userDetails) {
            loginId = userDetails.getUsername();
        } else if (principal instanceof String username) {
            loginId = username;
        } else {
            throw new RuntimeException("Cannot extract loginId from authentication");
        }

        return parentRepo.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("Authenticated parent not found in database"));
    }

}
