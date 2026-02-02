package com.master.practiceReact.service;

import com.master.practiceReact.Repository.ParentRepository;
import com.master.practiceReact.models.Entity.Parent;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class ParentDetailsService implements UserDetailsService {
    private final ParentRepository parentRepo;

    public ParentDetailsService(ParentRepository parentRepo) {
        this.parentRepo = parentRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Parent parent = parentRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Parent not found"));
        return new org.springframework.security.core.userdetails.User(
                parent.getEmail(),
                parent.getPassword(),
                parent.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                        .toList()
        );
    }
}
