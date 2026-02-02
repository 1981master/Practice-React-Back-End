package com.master.practiceReact.models.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private String name;
    private Integer displayOrder;

    public Topic() {
    }

    public Topic(Subject subject, String name, Integer displayOrder) {
        this.subject = subject;
        this.name = name;
        this.displayOrder = displayOrder;
    }

    public Long getId() { return id; }
    public Subject getSubject() { return subject; }
    public String getName() { return name; }
    public Integer getDisplayOrder() { return displayOrder; }

    public void setId(Long id) { this.id = id; }
    public void setSubject(Subject subject) { this.subject = subject; }
    public void setName(String name) { this.name = name; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
}
