package com.master.practiceReact.models.DTOs;

import java.util.List;

public class SubjectDTO {
    private Long id;
    private String name;
    private List<TopicDTO> topics;

    public SubjectDTO() {
    }

    public SubjectDTO(Long id, String name, List<TopicDTO> topics) {
        this.id = id;
        this.name = name;
        this.topics = topics;
    }

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

    public List<TopicDTO> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicDTO> topics) {
        this.topics = topics;
    }
}
