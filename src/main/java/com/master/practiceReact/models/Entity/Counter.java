package com.master.practiceReact.models.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "counter")
public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "kid_id", foreignKey = @ForeignKey(name = "fk_counter_kid"))
    private Kid kid;

    @Column(nullable = false)
    private int count;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    /** Constructors **/
    public Counter() {}

    public Counter(String type) {
        this.type = type;
    }

    public Counter(String type, Kid kid) {
        this.type = type;
        this.kid = kid;
    }

    public Counter(Long id, String type, Kid kid, int count, LocalDateTime lastUpdated) {
        this.id = id;
        this.type = type;
        this.kid = kid;
        this.count = count;
        this.lastUpdated = lastUpdated;
    }

    /** Lifecycle Callbacks **/
    @PrePersist
    public void prePersist() {
        if (lastUpdated == null) lastUpdated = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdated = LocalDateTime.now();
    }

    /** Getters & Setters **/
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Kid getKid() { return kid; }
    public void setKid(Kid kid) { this.kid = kid; }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }

    /** Utility Methods **/
    public void increment() {
        this.count++;
        this.lastUpdated = LocalDateTime.now();
    }

    public void increment(int value) {
        this.count += value;
        this.lastUpdated = LocalDateTime.now();
    }

    public void decrement() {
        if (this.count > 0) this.count--;
        this.lastUpdated = LocalDateTime.now();
    }

    public void reset() {
        this.count = 0;
        this.lastUpdated = LocalDateTime.now();
    }
}
