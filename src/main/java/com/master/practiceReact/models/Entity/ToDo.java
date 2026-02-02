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

    // ManyToOne mapping to Kid (optional)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "kid_id", foreignKey = @ForeignKey(name = "fk_todo_kid"))
    private Kid kid;

    @Column(length = 255, nullable = false)
    private String text;

    @Column(length = 500)
    private String note;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Priority priority;

    @Column(nullable = false)
    private boolean completed;

    @Column(nullable = false)
    private boolean archived;

    /** Constructors **/
    public ToDo() {}

    public ToDo(String text, Priority priority) {
        this.text = text;
        this.priority = priority;
    }

    public ToDo(String text, String note, Priority priority, Kid kid) {
        this.text = text;
        this.note = note;
        this.priority = priority;
        this.kid = kid;
    }

    public ToDo(Long id, Kid kid, String text, String note, LocalDateTime createdAt,
                LocalDateTime updatedAt, LocalDateTime completedAt,
                Priority priority, boolean completed, boolean archived) {
        this.id = id;
        this.kid = kid;
        this.text = text;
        this.note = note;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.completedAt = completedAt;
        this.priority = priority;
        this.completed = completed;
        this.archived = archived;
    }

    /** Lifecycle Callbacks **/
    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        completed = completed;  // ensures default false
        archived = archived;    // ensures default false
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /** Getters & Setters **/
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Kid getKid() { return kid; }
    public void setKid(Kid kid) { this.kid = kid; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public boolean getCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public boolean getArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }
}
