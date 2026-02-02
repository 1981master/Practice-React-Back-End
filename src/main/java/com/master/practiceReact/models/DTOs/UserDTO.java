package com.master.practiceReact.models.DTOs;

import com.master.practiceReact.models.Entity.Parent;
import com.master.practiceReact.models.Entity.Permission;
import com.master.practiceReact.models.Entity.Role;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {

    private Long id;
    private String email;
    private List<String> roles;
    private List<String> permissions;

    public UserDTO(Parent parent) {
        this.id = parent.getId();
        this.email = parent.getEmail();

        // Roles
        this.roles = parent.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        // Permissions via roles
        List<String> rolePermissions = parent.getRoles()
                .stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getName)
                .distinct()
                .collect(Collectors.toList());

        // Direct parent permissions
        List<String> directPermissions = parent.getParentPermissions()
                .stream()
                .map(pp -> pp.getPermission().getName())
                .collect(Collectors.toList());

        // Merge and remove duplicates
        this.permissions = rolePermissions;
        directPermissions.forEach(p -> {
            if (!this.permissions.contains(p)) {
                this.permissions.add(p);
            }
        });
    }

    // Getters
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public List<String> getRoles() { return roles; }
    public List<String> getPermissions() { return permissions; }
}
