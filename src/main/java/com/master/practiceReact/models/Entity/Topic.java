package com.master.practiceReact.models.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to Subject table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    @JsonBackReference
    private Subject subject;

    // Core topic fields
    private String name;
    private Integer displayOrder;

    // Optional metadata
    private String description;   // Short description of the topic
    private String icon;          // Emoji or small icon for UI
    private String gradeLevel;    // e.g., "K-2", "3-4", "5-6"
    private Boolean active = true; // To control if the topic is visible

    // Timestamps
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public Topic() { }

    public Topic(Subject subject, String name, Integer displayOrder, String icon, String gradeLevel) {
        this.subject = subject;
        this.name = name;
        this.displayOrder = displayOrder;
        this.icon = icon;
        this.gradeLevel = gradeLevel;
    }

    // Getters
    public Long getId() { return id; }
    public Subject getSubject() { return subject; }
    public String getName() { return name; }
    public Integer getDisplayOrder() { return displayOrder; }
    public String getDescription() { return description; }
    public String getIcon() { return icon; }
    public String getGradeLevel() { return gradeLevel; }
    public Boolean getActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setSubject(Subject subject) { this.subject = subject; }
    public void setName(String name) { this.name = name; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
    public void setDescription(String description) { this.description = description; }
    public void setIcon(String icon) { this.icon = icon; }
    public void setGradeLevel(String gradeLevel) { this.gradeLevel = gradeLevel; }
    public void setActive(Boolean active) { this.active = active; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Optional: lifecycle hooks to auto-set timestamps
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
