package com.master.practiceReact.controllers;

import com.master.practiceReact.Repository.TopicRepository;
import com.master.practiceReact.models.Entity.Topic;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
@CrossOrigin(origins = "http://localhost:3000")
public class TopicController {

    private final TopicRepository topicRepository;

    public TopicController(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    // Get all topics
    @GetMapping
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    // Get topics by subject ID
    @GetMapping("/subject/{subjectId}")
    public List<Topic> getTopicsBySubject(@PathVariable Long subjectId) {
        return topicRepository.findBySubjectId(subjectId);
    }
}
