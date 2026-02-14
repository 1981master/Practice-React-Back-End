package com.master.practiceReact.models.Entity;

import com.master.practiceReact.models.enums.Priority;
import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "todo")
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Parent The (owner)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parent_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_todo_parent"))
    private Parent parent;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "kid_id", nullable = true,
            foreignKey = @ForeignKey(name = "fk_todo_kid"))
    private Kid kid;

    @Column(nullable = false, length = 255)
    private String text;

    @Column(length = 500)
    private String note;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(nullable = false)
    private boolean completed = false;

    @Column(nullable = false)
    private boolean archived = false;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters & Setters remain the same
    public Long getId() { return id; }
    public Parent getParent() { return parent; }
    public Kid getKid() { return kid; }
    public String getText() { return text; }
    public String getNote() { return note; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public Priority getPriority() { return priority; }
    public boolean isCompleted() { return completed; }
    public boolean isArchived() { return archived; }

    public void setId(Long id) { this.id = id; }
    public void setParent(Parent parent) { this.parent = parent; }
    public void setKid(Kid kid) { this.kid = kid; }
    public void setText(String text) { this.text = text; }
    public void setNote(String note) { this.note = note; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public void setArchived(boolean archived) { this.archived = archived; }
}
