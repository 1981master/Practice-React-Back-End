package com.master.practiceReact.models.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "learning_session")
public class LearningSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kid_id")
    private Kid kid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attempt> attempts;

    public LearningSession() {
    }

    public LearningSession(Kid kid, Subject subject, LocalDateTime startTime, LocalDateTime endTime) {
        this.kid = kid;
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() { return id; }
    public Kid getKid() { return kid; }
    public Subject getSubject() { return subject; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public List<Attempt> getAttempts() { return attempts; }

    public void setId(Long id) { this.id = id; }
    public void setKid(Kid kid) { this.kid = kid; }
    public void setSubject(Subject subject) { this.subject = subject; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public void setAttempts(List<Attempt> attempts) { this.attempts = attempts; }
}
