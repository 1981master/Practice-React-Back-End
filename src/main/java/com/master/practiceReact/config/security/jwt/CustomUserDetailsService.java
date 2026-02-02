package com.master.practiceReact.config.security.jwt;

import com.master.practiceReact.Repository.ParentRepository;
import com.master.practiceReact.models.Entity.Parent;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ParentRepository parentRepository;

    public CustomUserDetailsService(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Parent parent = parentRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Parent not found: " + email));

        var authorities = parent.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                parent.getEmail(),
                parent.getPassword(),
                authorities
        );
    }
}
