package com.master.practiceReact.models.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    private Integer difficulty;
    private String questionText;
    private String correctAnswer;

    public Question() {
    }

    public Question(Topic topic, Integer difficulty, String questionText, String correctAnswer) {
        this.topic = topic;
        this.difficulty = difficulty;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public Long getId() { return id; }
    public Topic getTopic() { return topic; }
    public Integer getDifficulty() { return difficulty; }
    public String getQuestionText() { return questionText; }
    public String getCorrectAnswer() { return correctAnswer; }

    public void setId(Long id) { this.id = id; }
    public void setTopic(Topic topic) { this.topic = topic; }
    public void setDifficulty(Integer difficulty) { this.difficulty = difficulty; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
}
