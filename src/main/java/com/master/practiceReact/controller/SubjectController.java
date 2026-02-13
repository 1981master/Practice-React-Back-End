package com.master.practiceReact.controller;

import com.master.practiceReact.repository.SubjectRepository;
import com.master.practiceReact.models.DTOs.SubjectDTO;
import com.master.practiceReact.models.DTOs.TopicDTO;
import com.master.practiceReact.models.Entity.Subject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@CrossOrigin(origins = "http://localhost:3000")
public class SubjectController {

    private final SubjectRepository subjectRepository;

    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    // Get all subjects
    @GetMapping
    public List<SubjectDTO> getAllSubjects() {
        return subjectRepository.findAllWithTopics().stream()
                .map(subj -> new SubjectDTO(
                        subj.getId(),
                        subj.getName(),
                        subj.getTopics().stream()
                                .map(t -> new TopicDTO(t.getId(), t.getName()))
                                .toList()
                ))
                .toList();
    }

    // Get one subject with its topics
    @GetMapping("/{id}")
    public Subject getSubjectWithTopics(@PathVariable Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id " + id));
    }
}
