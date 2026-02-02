package com.master.practiceReact.models.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attempt")
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private LearningSession session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    private String givenAnswer;
    private boolean correct;
    private Long timeTakenMs;
    private LocalDateTime attemptedAt;

    public Attempt() {
    }

    public Attempt(LearningSession session, Question question, String givenAnswer, boolean correct, Long timeTakenMs) {
        this.session = session;
        this.question = question;
        this.givenAnswer = givenAnswer;
        this.correct = correct;
        this.timeTakenMs = timeTakenMs;
        this.attemptedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public LearningSession getSession() { return session; }
    public Question getQuestion() { return question; }
    public String getGivenAnswer() { return givenAnswer; }
    public boolean isCorrect() { return correct; }
    public Long getTimeTakenMs() { return timeTakenMs; }
    public LocalDateTime getAttemptedAt() { return attemptedAt; }

    public void setId(Long id) { this.id = id; }
    public void setSession(LearningSession session) { this.session = session; }
    public void setQuestion(Question question) { this.question = question; }
    public void setGivenAnswer(String givenAnswer) { this.givenAnswer = givenAnswer; }
    public void setCorrect(boolean correct) { this.correct = correct; }
    public void setTimeTakenMs(Long timeTakenMs) { this.timeTakenMs = timeTakenMs; }
    public void setAttemptedAt(LocalDateTime attemptedAt) { this.attemptedAt = attemptedAt; }
}

