package com.master.practiceReact.config.security.jwt;

import com.master.practiceReact.Repository.ParentRepository;
import com.master.practiceReact.models.Entity.Parent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ParentRepository parentRepository;
    Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    public CustomUserDetailsService(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    @Override
    @Transactional(readOnly = true) //keeps Hibernate session open for lazy collections
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        Parent parent = (Parent) parentRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("Parent not found: " + loginId));

        // Eagerly load roles and permissions to avoid LazyInitializationException
        parent.getRoles().forEach(role -> role.getPermissions().size());

        List<GrantedAuthority> authorities = parent.getRoles().stream()
                .flatMap(role -> {
                    // role authority
                    GrantedAuthority roleAuthority = new SimpleGrantedAuthority("ROLE_" + role.getName());

                    // permissions authority (exact string as in hasAuthority)
                    Stream<GrantedAuthority> permissionAuthorities = role.getPermissions().stream()
                            .map(p -> new SimpleGrantedAuthority(p.getName()));

                    // combine role + permissions
                    return Stream.concat(permissionAuthorities, Stream.of(roleAuthority));
                })
                .collect(Collectors.toList());

        authorities.forEach(a -> logger.info("Granted authority: {}", a.getAuthority()));

        return new org.springframework.security.core.userdetails.User(
                parent.getLoginId(),
                parent.getPassword(),
                authorities
        );
    }
}
