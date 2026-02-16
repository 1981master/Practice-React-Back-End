package com.master.practiceReact.models.DTOs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.master.practiceReact.models.Entity.Kid;
import com.master.practiceReact.models.Entity.ParentPermission;
import com.master.practiceReact.models.Entity.Permission;
import com.master.practiceReact.models.Entity.ToDo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class ParentDTO {

    private Long id;
    private String loginId;
    private String email;
    private LocalDateTime createdAt;
    private Set<String> roles;
    @JsonBackReference
    private Set<Kid> kids;
    private Set<ParentPermission> permissions;
    private Set<ToDo> todos;

    public ParentDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<Kid> getKids() {
        return kids;
    }

    public void setKids(Set<Kid> kids) {
        this.kids = kids;
    }

    public Set<ParentPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<ParentPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ToDo> getTodos() {
        return todos;
    }

    public void setTodos(Set<ToDo> todos) {
        this.todos = todos;
    }
}
