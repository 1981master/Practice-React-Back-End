package com.master.practiceReact.models.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "parent")
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "login_id", nullable = false, unique = true, length = 50)
    private String loginId;
    @Column(nullable = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // One parent can have many kids
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Kid> kids = new HashSet<>();

    // One parent can have many permissions
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ParentPermission> parentPermissions = new HashSet<>();

    // Parent roles (many-to-many)
    @ManyToMany
    @JoinTable(
            name = "parent_role",
            joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    /** Constructors **/
    public Parent() {}

    public Parent(Long id, String email, String password, String loginId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.loginId = loginId;
    }

    /** Lifecycle Callback **/
    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    /** Getters & Setters **/
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Set<Kid> getKids() { return kids; }
    public void setKids(Set<Kid> kids) { this.kids = kids; }

    public Set<ParentPermission> getParentPermissions() { return parentPermissions; }
    public void setParentPermissions(Set<ParentPermission> parentPermissions) { this.parentPermissions = parentPermissions; }

    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
}
