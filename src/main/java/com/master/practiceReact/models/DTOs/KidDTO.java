package com.master.practiceReact.models.DTOs;

import com.master.practiceReact.models.Entity.Kid;

import java.time.LocalDateTime;

public class KidDTO {
    private Long id;
    private String name;
    private Integer age;
    private String grade;
    private LocalDateTime createdAt;
    private Kid kid;

    public KidDTO() {
    }

//    public KidDTO(Kid kid) {
//        this.kid.setCreatedAt(kid.getCreatedAt());
//        this.kid.setAge(kid.getAge());
//        this.kid.setId(kid.getId());
//        this.kid.setGrade(kid.getGrade());
//        this.kid.setName(kid.getName());
//        this.kid.setParent(kid.getParent());
//
//    }
//
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
}
