package com.master.practiceReact.service;

import com.master.practiceReact.Repository.PermissionRepository;
import com.master.practiceReact.Repository.RoleRepository;
import com.master.practiceReact.models.Entity.Permission;
import com.master.practiceReact.models.Entity.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolePermissionSetupService {

    private final RoleRepository roleRepo;
    private final PermissionRepository permissionRepo;

    public RolePermissionSetupService(RoleRepository roleRepo, PermissionRepository permissionRepo) {
        this.roleRepo = roleRepo;
        this.permissionRepo = permissionRepo;
    }

    @Transactional
    public void setupRolePermissions() {
        Role admin = (Role) roleRepo.findByName("ADMIN").orElseGet(() -> roleRepo.save(new Role("ADMIN")));
        Role user = (Role) roleRepo.findByName("USER").orElseGet(() -> roleRepo.save(new Role("USER")));

        Permission viewKids = permissionRepo.findByName("VIEW_KIDS").orElseGet(() -> permissionRepo.save(new Permission("VIEW_KIDS")));
        Permission viewTodos = permissionRepo.findByName("VIEW_TODOS").orElseGet(() -> permissionRepo.save(new Permission("VIEW_TODOS")));
        Permission viewTopics = permissionRepo.findByName("VIEW_TOPICS").orElseGet(() -> permissionRepo.save(new Permission("VIEW_TOPICS")));
        Permission viewAnalytics = permissionRepo.findByName("VIEW_ANALYTICS").orElseGet(() -> permissionRepo.save(new Permission("VIEW_ANALYTICS")));

        // Assign permissions to roles
        admin.getPermissions().addAll(List.of(viewKids, viewTodos, viewTopics, viewAnalytics));
        user.getPermissions().addAll(List.of(viewKids, viewTodos));

        roleRepo.save(admin);
        roleRepo.save(user);
    }

}

