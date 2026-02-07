package com.master.practiceReact.models.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "kid")
public class Kid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Optional parent (nullable)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_kid_parent"))
    private Parent parent;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = true)
    private Integer age;

    @Column(nullable = true, length = 50)
    private String grade;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "child_loginId")
    private String childLoginId;

    @Column(nullable = true)
    private String password;

    /** --- Cascade relations --- **/
    @OneToMany(
            mappedBy = "kid",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<LearningSession> learningSessions;

    @OneToMany(
            mappedBy = "kid",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<AnalyticsSnapshot> analyticsSnapshots;

    @OneToMany(
            mappedBy = "kid",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Counter> counters;

    @OneToMany(
            mappedBy = "kid",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Recommendation> recommendations;

    @OneToMany(
            mappedBy = "kid",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ParentTopicSettings> parentTopicSettings;

    @OneToMany(
            mappedBy = "kid",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TopicProgress> topicProgresses;

    @OneToMany(
            mappedBy = "kid",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ToDo> todos;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "kid_role",
            joinColumns = @JoinColumn(name = "kid_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    /** Constructors **/
    public Kid() {}

    public Kid(Parent parent, String name, Integer age, String grade, String childLoginId, String password, List<ToDo> todos, Set<Role> roles) {
        this.parent = parent;
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.childLoginId = childLoginId;
        this.password = password;
        this.todos = todos;
        this.roles = roles;
    }

    /** Lifecycle Callback **/
    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
    /** Getters & Setters **/
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Parent getParent() { return parent; }
    public void setParent(Parent parent) { this.parent = parent; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getChildLoginId() { return childLoginId; }
    public void setChildLoginId(String childLoginId) { this.childLoginId = childLoginId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<LearningSession> getLearningSessions() { return learningSessions; }
    public void setLearningSessions(List<LearningSession> learningSessions) { this.learningSessions = learningSessions; }

    public List<AnalyticsSnapshot> getAnalyticsSnapshots() { return analyticsSnapshots; }
    public void setAnalyticsSnapshots(List<AnalyticsSnapshot> analyticsSnapshots) { this.analyticsSnapshots = analyticsSnapshots; }

    public List<Counter> getCounters() { return counters; }
    public void setCounters(List<Counter> counters) { this.counters = counters; }

    public List<Recommendation> getRecommendations() { return recommendations; }
    public void setRecommendations(List<Recommendation> recommendations) { this.recommendations = recommendations; }

    public List<ParentTopicSettings> getParentTopicSettings() { return parentTopicSettings; }
    public void setParentTopicSettings(List<ParentTopicSettings> parentTopicSettings) { this.parentTopicSettings = parentTopicSettings; }

    public List<TopicProgress> getTopicProgresses() { return topicProgresses; }
    public void setTopicProgresses(List<TopicProgress> topicProgresses) { this.topicProgresses = topicProgresses; }

    public List<ToDo> getTodos() { return todos; }
    public void setTodos(List<ToDo> todos) { this.todos = todos; }

    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }

}
