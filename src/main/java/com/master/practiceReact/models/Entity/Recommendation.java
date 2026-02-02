package com.master.practiceReact.models.Entity;

import com.master.practiceReact.models.enums.RecommendationStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendation")
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kid_id")
    private Kid kid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = true)
    private Topic topic; // optional, can be null if general recommendation

    @Column(length = 500)
    private String message;

    @Enumerated(EnumType.STRING)
    private RecommendationStatus status; // NEW, READ, IGNORED

    private LocalDateTime createdAt;

    public Recommendation() {
    }

    public Recommendation(Kid kid, Topic topic, String message, RecommendationStatus status) {
        this.kid = kid;
        this.topic = topic;
        this.message = message;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Kid getKid() { return kid; }
    public Topic getTopic() { return topic; }
    public String getMessage() { return message; }
    public RecommendationStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setKid(Kid kid) { this.kid = kid; }
    public void setTopic(Topic topic) { this.topic = topic; }
    public void setMessage(String message) { this.message = message; }
    public void setStatus(RecommendationStatus status) { this.status = status; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
