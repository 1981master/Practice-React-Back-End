package com.master.practiceReact.models.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "analytics_snapshot")
public class AnalyticsSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kid_id")
    private Kid kid;

    private LocalDate snapshotDate;

    private int totalTimeMinutes;
    private int totalAttempts;
    private int correctAttempts;
    private double accuracy;

    public AnalyticsSnapshot() {
    }

    public AnalyticsSnapshot(Kid kid, LocalDate snapshotDate, int totalTimeMinutes,
                             int totalAttempts, int correctAttempts, double accuracy) {
        this.kid = kid;
        this.snapshotDate = snapshotDate;
        this.totalTimeMinutes = totalTimeMinutes;
        this.totalAttempts = totalAttempts;
        this.correctAttempts = correctAttempts;
        this.accuracy = accuracy;
    }

    public Long getId() { return id; }
    public Kid getKid() { return kid; }
    public LocalDate getSnapshotDate() { return snapshotDate; }
    public int getTotalTimeMinutes() { return totalTimeMinutes; }
    public int getTotalAttempts() { return totalAttempts; }
    public int getCorrectAttempts() { return correctAttempts; }
    public double getAccuracy() { return accuracy; }

    public void setId(Long id) { this.id = id; }
    public void setKid(Kid kid) { this.kid = kid; }
    public void setSnapshotDate(LocalDate snapshotDate) { this.snapshotDate = snapshotDate; }
    public void setTotalTimeMinutes(int totalTimeMinutes) { this.totalTimeMinutes = totalTimeMinutes; }
    public void setTotalAttempts(int totalAttempts) { this.totalAttempts = totalAttempts; }
    public void setCorrectAttempts(int correctAttempts) { this.correctAttempts = correctAttempts; }
    public void setAccuracy(double accuracy) { this.accuracy = accuracy; }
}
