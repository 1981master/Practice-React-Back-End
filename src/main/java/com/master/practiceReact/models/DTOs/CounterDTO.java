package com.master.practiceReact.models.DTOs;

import java.time.LocalDateTime;

public class CounterDTO {

    private Long id;
    private String type;    // CounterType enum as string
    private Long kidId;     // nullable, null = global
    private int count;      // the counter value
    private LocalDateTime lastUpdated;

    public CounterDTO() {
    }

    public CounterDTO(Long id, String type, Long kidId, int count, LocalDateTime lastUpdated) {
        this.id = id;
        this.type = type;
        this.kidId = kidId;
        this.count = count;
        this.lastUpdated = lastUpdated;
    }

    // ------------------- Getters -------------------
    public Long getId() { return id; }
    public String getType() { return type; }
    public Long getKidId() { return kidId; }
    public int getCount() { return count; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }

    // ------------------- Setters -------------------
    public void setId(Long id) { this.id = id; }
    public void setType(String type) { this.type = type; }
    public void setKidId(Long kidId) { this.kidId = kidId; }
    public void setCount(int count) { this.count = count; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}
