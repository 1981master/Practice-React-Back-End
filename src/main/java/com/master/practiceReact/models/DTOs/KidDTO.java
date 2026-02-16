package com.master.practiceReact.models.DTOs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.master.practiceReact.models.Entity.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class KidDTO {
    private Long id;
    private String name;
    private Integer age;
    private String grade;
    private LocalDateTime createdAt;
    private String childLoginId;
    private String password;
    @JsonBackReference
    private Parent parent;
    private List<LearningSession> learningSessions;
    private List<AnalyticsSnapshot> analyticsSnapshot;
    private List<Counter> counters;
    private List<Recommendation> recommendation;
    private List<ParentTopicSettings> parentTopicSettings;
    private List<TopicProgress> topicProgresses;
    private List<ToDo> todo;
    private Set<Role> roles;

    public KidDTO() {
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getChildLoginId() {
        return childLoginId;
    }

    public void setChildLoginId(String childLoginId) {
        this.childLoginId = childLoginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public List<LearningSession> getLearningSessions() {
        return learningSessions;
    }

    public void setLearningSessions(List<LearningSession> learningSessions) {
        this.learningSessions = learningSessions;
    }

    public List<AnalyticsSnapshot> getAnalyticsSnapshot() {
        return analyticsSnapshot;
    }

    public void setAnalyticsSnapshot(List<AnalyticsSnapshot> analyticsSnapshot) {
        this.analyticsSnapshot = analyticsSnapshot;
    }

    public List<Counter> getCounters() {
        return counters;
    }

    public void setCounters(List<Counter> counters) {
        this.counters = counters;
    }

    public List<Recommendation> getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(List<Recommendation> recommendation) {
        this.recommendation = recommendation;
    }

    public List<ParentTopicSettings> getParentTopicSettings() {
        return parentTopicSettings;
    }

    public void setParentTopicSettings(List<ParentTopicSettings> parentTopicSettings) {
        this.parentTopicSettings = parentTopicSettings;
    }

    public List<TopicProgress> getTopicProgresses() {
        return topicProgresses;
    }

    public void setTopicProgresses(List<TopicProgress> topicProgresses) {
        this.topicProgresses = topicProgresses;
    }

    public List<ToDo> getTodo() {
        return todo;
    }

    public void setTodo(List<ToDo> todo) {
        this.todo = todo;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
