package com.master.practiceReact.models.DTOs;

import com.master.practiceReact.models.Entity.Kid;

import java.time.LocalDateTime;

public class KidDTO {
    private Long id;
    private String name;
    private Integer age;
    private String grade;
    private LocalDateTime createdAt;
    private String childLoginId;
    private String password;
    public KidDTO() {
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getChildLoginId() {
        return childLoginId;
    }

    public void setChildLoginId(String childLoginId) {
        this.childLoginId = childLoginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
