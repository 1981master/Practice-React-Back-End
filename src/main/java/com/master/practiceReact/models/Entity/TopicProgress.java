package com.master.practiceReact.models.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "topic_progress",
        uniqueConstraints = @UniqueConstraint(columnNames = {"kid_id","topic_id"})
)
public class TopicProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Kid kid;

    @ManyToOne(fetch = FetchType.LAZY)
    private Topic topic;

    private int totalAttempts;
    private int correctAttempts;
    private double accuracy;
    private String masteryLevel;
    private Integer currentDifficulty;
    private LocalDateTime lastUpdated;

    public TopicProgress() {
    }

    public TopicProgress(Kid kid, Topic topic) {
        this.kid = kid;
        this.topic = topic;
        this.lastUpdated = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Kid getKid() { return kid; }
    public Topic getTopic() { return topic; }
    public int getTotalAttempts() { return totalAttempts; }
    public int getCorrectAttempts() { return correctAttempts; }
    public double getAccuracy() { return accuracy; }
    public String getMasteryLevel() { return masteryLevel; }
    public Integer getCurrentDifficulty() { return currentDifficulty; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }

    public void setId(Long id) { this.id = id; }
    public void setKid(Kid kid) { this.kid = kid; }
    public void setTopic(Topic topic) { this.topic = topic; }
    public void setTotalAttempts(int totalAttempts) { this.totalAttempts = totalAttempts; }
    public void setCorrectAttempts(int correctAttempts) { this.correctAttempts = correctAttempts; }
    public void setAccuracy(double accuracy) { this.accuracy = accuracy; }
    public void setMasteryLevel(String masteryLevel) { this.masteryLevel = masteryLevel; }
    public void setCurrentDifficulty(Integer currentDifficulty) { this.currentDifficulty = currentDifficulty; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}
