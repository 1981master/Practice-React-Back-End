package com.master.practiceReact.service;

import com.master.practiceReact.Repository.ParentRepository;
import com.master.practiceReact.models.Entity.Parent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class ParentDetailsService implements UserDetailsService {

    private final ParentRepository parentRepo;
    private final Logger logger = LoggerFactory.getLogger(ParentDetailsService.class);
    public ParentDetailsService(ParentRepository parentRepo) {
        this.parentRepo = parentRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier)
            throws UsernameNotFoundException {
        logger.info("Trying to log in using ID/Email: {}", identifier);
        Parent parent = (Parent) parentRepo
                .findByLoginIdOrEmail(identifier, identifier)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Invalid Parent ID or password")
                );

        return new org.springframework.security.core.userdetails.User(
                parent.getLoginId(),
                parent.getPassword(),
                parent.getRoles().stream()
                        .map(role ->
                                new SimpleGrantedAuthority("ROLE_" + role.getName())
                        )
                        .toList()
        );
    }
}
