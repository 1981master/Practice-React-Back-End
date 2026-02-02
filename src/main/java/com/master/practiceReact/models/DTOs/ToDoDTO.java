package com.master.practiceReact.models.DTOs;

import com.master.practiceReact.models.enums.Priority;
import java.time.LocalDateTime;

public class ToDoDTO {

    private Long id;
    private Long kidId;
    private String text;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
    private Priority priority;
    private Boolean completed;
    private Boolean archived;

    // ===================== Constructors =====================

    public ToDoDTO() {}

    public ToDoDTO(Long id, Long kidId, String text, String note, LocalDateTime createdAt,
                   LocalDateTime updatedAt, LocalDateTime completedAt, Priority priority,
                   Boolean completed, Boolean archived) {
        this.id = id;
        this.kidId = kidId;
        this.text = text;
        this.note = note;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.completedAt = completedAt;
        this.priority = priority;
        this.completed = completed;
        this.archived = archived;
    }

    // ===================== Getters & Setters =====================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getKidId() { return kidId; }
    public void setKidId(Long kidId) { this.kidId = kidId; }

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

    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }

    public Boolean getArchived() { return archived; }
    public void setArchived(Boolean archived) { this.archived = archived; }

}
