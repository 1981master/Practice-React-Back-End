package com.master.practiceReact.models.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "kid")
public class Kid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Optional parent (nullable)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_kid_parent"))
    private Parent parent;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = true)
    private Integer age;

    @Column(nullable = true, length = 50)
    private String grade;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /** Constructors **/
    public Kid() {}

    public Kid(Parent parent, String name, Integer age, String grade) {
        this.parent = parent;
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    /** Lifecycle Callback **/
    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    /** Getters & Setters **/
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Parent getParent() { return parent; }
    public void setParent(Parent parent) { this.parent = parent; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
