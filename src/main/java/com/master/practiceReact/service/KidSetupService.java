package com.master.practiceReact.service;

import com.master.practiceReact.repository.KidRepository;
import com.master.practiceReact.repository.PermissionRepository;
import com.master.practiceReact.repository.RoleRepository;
import com.master.practiceReact.models.Entity.Kid;
import com.master.practiceReact.models.Entity.Permission;
import com.master.practiceReact.models.Entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class KidSetupService {

    private static final Logger logger = LoggerFactory.getLogger(KidSetupService.class);

    private final RoleRepository roleRepo;
    private final KidRepository kidRepo;
    private final PermissionRepository permissionRepo;

    public KidSetupService(RoleRepository roleRepo, KidRepository kidRepo, PermissionRepository permissionRepo) {
        this.roleRepo = roleRepo;
        this.kidRepo = kidRepo;
        this.permissionRepo = permissionRepo;
    }

    @Transactional
    public Kid registerKid(Kid kid) {
        logger.info("Saving new kid with Login ID: {}", kid.getChildLoginId());
        Kid savedKid = kidRepo.save(kid);

        // Fetch or create default kid role
        Role kidRole = (Role) roleRepo.findByName("KID_ROLE")
                .orElseGet(() -> roleRepo.save(new Role("KID_ROLE")));

        // Assign default permissions if empty
        if (kidRole.getPermissions().isEmpty()) {
            List<String> kidPerms = List.of("VIEW_TODOS", "VIEW_TOPICS"); // default permissions for kids
            for (String permName : kidPerms) {
                Permission perm = permissionRepo.findByName(permName)
                        .orElseGet(() -> permissionRepo.save(new Permission(permName)));
                kidRole.getPermissions().add(perm);
                logger.info("Granted permission '{}' to KID_ROLE", permName);
            }
            roleRepo.save(kidRole); // save role with permissions
        }

        // Assign role to kid
        savedKid.setRoles(Set.of(kidRole)); // assuming List<Role> roles in Kid entity
        kidRepo.save(savedKid);

        logger.info("Roles and permissions successfully assigned to Kid Login ID: {}", savedKid.getChildLoginId());

        return savedKid;
    }
}
