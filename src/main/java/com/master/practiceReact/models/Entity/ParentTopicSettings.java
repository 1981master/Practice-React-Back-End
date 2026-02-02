package com.master.practiceReact.models.Entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "parent_topic_settings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"parent_id","kid_id","topic_id"})
)
public class ParentTopicSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Parent parent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Kid kid;

    @ManyToOne(fetch = FetchType.LAZY)
    private Topic topic;

    private boolean enabled;
    private Integer minDifficulty;
    private Integer maxDifficulty;
    private boolean adaptiveEnabled;

    public ParentTopicSettings() {
    }

    public ParentTopicSettings(Parent parent, Kid kid, Topic topic,
                               Integer minDifficulty, Integer maxDifficulty, boolean adaptiveEnabled) {
        this.parent = parent;
        this.kid = kid;
        this.topic = topic;
        this.minDifficulty = minDifficulty;
        this.maxDifficulty = maxDifficulty;
        this.adaptiveEnabled = adaptiveEnabled;
        this.enabled = true;
    }

    public Long getId() { return id; }
    public Parent getParent() { return parent; }
    public Kid getKid() { return kid; }
    public Topic getTopic() { return topic; }
    public boolean isEnabled() { return enabled; }
    public Integer getMinDifficulty() { return minDifficulty; }
    public Integer getMaxDifficulty() { return maxDifficulty; }
    public boolean isAdaptiveEnabled() { return adaptiveEnabled; }

    public void setId(Long id) { this.id = id; }
    public void setParent(Parent parent) { this.parent = parent; }
    public void setKid(Kid kid) { this.kid = kid; }
    public void setTopic(Topic topic) { this.topic = topic; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public void setMinDifficulty(Integer minDifficulty) { this.minDifficulty = minDifficulty; }
    public void setMaxDifficulty(Integer maxDifficulty) { this.maxDifficulty = maxDifficulty; }
    public void setAdaptiveEnabled(boolean adaptiveEnabled) { this.adaptiveEnabled = adaptiveEnabled; }
}
