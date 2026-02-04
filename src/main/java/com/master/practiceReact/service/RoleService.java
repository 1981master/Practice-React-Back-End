package com.master.practiceReact.service;

import com.master.practiceReact.models.Entity.Role;
import com.master.practiceReact.Repository.RoleRepository;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void initRoles() {
        if (!roleRepository.existsByName("ADMIN")) {
            roleRepository.save(new Role(null, "ADMIN"));
        }
        if (!roleRepository.existsByName("USER")) {
            roleRepository.save(new Role(null, "USER"));
        }
    }
}
