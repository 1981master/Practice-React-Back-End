package com.master.practiceReact.controllers;

import com.master.practiceReact.models.events.KafkaLessonCompletedEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    @PostMapping("/complete")
    public String completeLesson(@RequestBody KafkaLessonCompletedEvent event) {
        return "Lesson event sent!";
    }
}

