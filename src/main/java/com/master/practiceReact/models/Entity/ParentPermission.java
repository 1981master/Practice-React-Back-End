package com.master.practiceReact.models.Entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "parent_permission")
@IdClass(ParentPermissionId.class)
public class ParentPermission implements Serializable {

    @Id
    @Column(name = "parent_id")
    private Long parentId;

    @Id
    @Column(name = "permission_id")
    private Long permissionId;

    @Column(name = "granted_at", nullable = false)
    private LocalDateTime grantedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Parent parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", insertable = false, updatable = false)
    private Permission permission;

    /** Constructors **/
    public ParentPermission() {
        this.grantedAt = LocalDateTime.now();
    }

    public ParentPermission(Long parentId, Long permissionId) {
        this.parentId = parentId;
        this.permissionId = permissionId;
        this.grantedAt = LocalDateTime.now();
    }

    /** Lifecycle Callback **/
    @PrePersist
    public void prePersist() {
        if (grantedAt == null) grantedAt = LocalDateTime.now();
    }

    /** Getters & Setters **/
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public Long getPermissionId() { return permissionId; }
    public void setPermissionId(Long permissionId) { this.permissionId = permissionId; }

    public LocalDateTime getGrantedAt() { return grantedAt; }
    public void setGrantedAt(LocalDateTime grantedAt) { this.grantedAt = grantedAt; }

    public Parent getParent() { return parent; }
    public void setParent(Parent parent) { this.parent = parent; }

    public Permission getPermission() { return permission; }
    public void setPermission(Permission permission) { this.permission = permission; }
}
