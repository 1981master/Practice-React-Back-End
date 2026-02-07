package com.master.practiceReact.config.security.jwt;

import com.master.practiceReact.Repository.ParentRepository;
import com.master.practiceReact.Repository.KidRepository;
import com.master.practiceReact.models.Entity.Parent;
import com.master.practiceReact.models.Entity.Kid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ParentRepository parentRepository;
    private final KidRepository kidRepository;
    Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    public CustomUserDetailsService(ParentRepository parentRepository, KidRepository kidRepository) {
        this.parentRepository = parentRepository;
        this.kidRepository = kidRepository;
    }

    // =========================
    // Original parent-only implementation (kept commented)
    // =========================
//    @Override
//    @Transactional(readOnly = true) //keeps Hibernate session open for lazy collections
//    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
//
//        Parent parent = (Parent) parentRepository.findByLoginId(loginId)
//                .orElseThrow(() -> new UsernameNotFoundException("Parent not found: " + loginId));
//
//        // Eagerly load roles and permissions to avoid LazyInitializationException
//        parent.getRoles().forEach(role -> role.getPermissions().size());
//
//        List<GrantedAuthority> authorities = parent.getRoles().stream()
//                .flatMap(role -> {
//                    // role authority
//                    GrantedAuthority roleAuthority = new SimpleGrantedAuthority("ROLE_" + role.getName());
//
//                    // permissions authority (exact string as in hasAuthority)
//                    Stream<GrantedAuthority> permissionAuthorities = role.getPermissions().stream()
//                            .map(p -> new SimpleGrantedAuthority(p.getName()));
//
//                    // combine role + permissions
//                    return Stream.concat(permissionAuthorities, Stream.of(roleAuthority));
//                })
//                .collect(Collectors.toList());
//
//        authorities.forEach(a -> logger.info("Granted authority: {}", a.getAuthority()));
//
//        return new org.springframework.security.core.userdetails.User(
//                parent.getLoginId(),
//                parent.getPassword(),
//                authorities
//        );
//    }

    // =========================
    // Unified parent + kid login
    // =========================
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        // Try Parent first
        Optional<Parent> parentOpt = parentRepository.findByLoginId(loginId);
        if (parentOpt.isPresent()) {
            Parent parent = parentOpt.get();
            // Eagerly load roles/permissions
            parent.getRoles().forEach(r -> r.getPermissions().size());
            List<GrantedAuthority> authorities = parent.getRoles().stream()
                    .flatMap(role -> {
                        GrantedAuthority roleAuthority = new SimpleGrantedAuthority("ROLE_" + role.getName());
                        Stream<GrantedAuthority> permissionAuthorities = role.getPermissions().stream()
                                .map(p -> new SimpleGrantedAuthority(p.getName()));
                        return Stream.concat(permissionAuthorities, Stream.of(roleAuthority));
                    }).collect(Collectors.toList());

            return new org.springframework.security.core.userdetails.User(
                    parent.getLoginId(),
                    parent.getPassword(),
                    authorities
            );
        }

        // Try Kid next
        Optional<Kid> kidOpt = kidRepository.findByChildLoginId(loginId);
        if (kidOpt.isPresent()) {
            Kid kid = kidOpt.get();

            // Eagerly load roles and permissions
            kid.getRoles().forEach(role -> role.getPermissions().size());

            List<GrantedAuthority> authorities = kid.getRoles().stream()
                    .flatMap(role -> {
                        GrantedAuthority roleAuthority = new SimpleGrantedAuthority("ROLE_" + role.getName());
                        Stream<GrantedAuthority> permissionAuthorities = role.getPermissions().stream()
                                .map(p -> new SimpleGrantedAuthority(p.getName()));
                        return Stream.concat(permissionAuthorities, Stream.of(roleAuthority));
                    }).collect(Collectors.toList());

            return new org.springframework.security.core.userdetails.User(
                    kid.getChildLoginId(),
                    kid.getPassword(),
                    authorities
            );
        }
        throw new UsernameNotFoundException("User not found: " + loginId);
    }
}
