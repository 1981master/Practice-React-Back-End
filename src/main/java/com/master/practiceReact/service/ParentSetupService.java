package com.master.practiceReact.service;

import com.master.practiceReact.repository.ParentPermissionsRepository;
import com.master.practiceReact.repository.ParentRepository;
import com.master.practiceReact.repository.PermissionRepository;
import com.master.practiceReact.repository.RoleRepository;
import com.master.practiceReact.models.Entity.Parent;
import com.master.practiceReact.models.Entity.Permission;
import com.master.practiceReact.models.Entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParentSetupService {

    private static final Logger logger = LoggerFactory.getLogger(ParentSetupService.class);

    private final RoleRepository roleRepo;
    private final ParentRepository parentRepo;
    private final PermissionRepository permissionRepo;
    private final ParentPermissionsRepository parentPermissionsRepository;

    public ParentSetupService(RoleRepository roleRepo, ParentRepository parentRepo,
                              PermissionRepository permissionRepo, ParentPermissionsRepository parentPermissionsRepository) {
        this.roleRepo = roleRepo;
        this.parentRepo = parentRepo;
        this.permissionRepo = permissionRepo;
        this.parentPermissionsRepository = parentPermissionsRepository;
    }

    @Transactional
    public Parent registerFirstParent(Parent parent) {
        logger.info("Saving new parent with Parent ID: {}", parent.getLoginId());
        Parent savedParent = parentRepo.save(parent);

        long count = parentRepo.count();
        if (count == 1) { // First user
            logger.info("First user detected, setting up roles and permissions for Parent ID: {}", savedParent.getLoginId());

            // Only fetch/create ADMIN and USER roles
            Role adminRole = (Role) roleRepo.findByName("ADMIN").orElseGet(() -> roleRepo.save(new Role("ADMIN")));
            Role userRole = (Role) roleRepo.findByName("USER").orElseGet(() -> roleRepo.save(new Role("USER")));

            // Assign only ADMIN role to first parent
            savedParent.getRoles().add(adminRole);

            // eeee$p role fzermissions if empty
            if (adminRole.getPermissions().isEmpty()) {
                List<String> adminPerms = List.of("VIEW_KIDS", "VIEW_TOPICS", "VIEW_TODOS", "VIEW_ANALYTICS");
                for (String permName : adminPerms) {
                    Permission perm = permissionRepo.findByName(permName)
                            .orElseGet(() -> permissionRepo.save(new Permission(permName)));
                    adminRole.getPermissions().add(perm);
                    logger.info("Granted permission '{}' to ADMIN role", permName);
                }
                roleRepo.save(adminRole); // Save updated role-permissions
            }

            if (userRole.getPermissions().isEmpty()) {
                List<String> userPerms = List.of("VIEW_KIDS", "VIEW_TODOS"); // limited permissions
                for (String permName : userPerms) {
                    Permission perm = permissionRepo.findByName(permName)
                            .orElseGet(() -> permissionRepo.save(new Permission(permName)));
                    userRole.getPermissions().add(perm);
                    logger.info("Granted permission '{}' to USER role", permName);
                }
                roleRepo.save(userRole);
            }

            logger.info("Roles and permissions successfully assigned to Parent ID: {}", savedParent.getLoginId());
        } else {
            logger.info("Parent ID: {} is not the first user. Skipping role/permission setup.", savedParent.getLoginId());
        }

        return savedParent;
    }

}
