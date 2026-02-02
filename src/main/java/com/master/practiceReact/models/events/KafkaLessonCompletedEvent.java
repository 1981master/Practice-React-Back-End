package com.master.practiceReact.models.events;

import java.time.Instant;

public class KafkaLessonCompletedEvent {

    private Long studentId;
    private String grade; // "K", "1", "5"
    private String lessonId;
    private Instant completedAt;

    // Constructors
    public KafkaLessonCompletedEvent() {}

    public KafkaLessonCompletedEvent(Long studentId, String grade, String lessonId, Instant completedAt) {
        this.studentId = studentId;
        this.grade = grade;
        this.lessonId = lessonId;
        this.completedAt = completedAt;
    }

    // Getters & Setters
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getLessonId() { return lessonId; }
    public void setLessonId(String lessonId) { this.lessonId = lessonId; }
    public Instant getCompletedAt() { return completedAt; }
    public void setCompletedAt(Instant completedAt) { this.completedAt = completedAt; }

}
