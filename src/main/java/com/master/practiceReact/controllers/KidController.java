package com.master.practiceReact.controllers;

import com.master.practiceReact.Repository.KidRepository;
import com.master.practiceReact.Repository.ParentRepository;
import com.master.practiceReact.models.Entity.Kid;
import com.master.practiceReact.models.Entity.Parent;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/kids")
public class KidController {

    private final KidRepository kidRepository;
    private final ParentRepository parentRepository;

    public KidController(KidRepository kidRepository, ParentRepository parentRepository) {
        this.kidRepository = kidRepository;
        this.parentRepository = parentRepository;
    }

    @GetMapping
    public List<Kid> getAll() {
        return kidRepository.findAll();
    }

    @GetMapping("/parent/{parentId}")
    public List<Kid> getByParent(@PathVariable Long parentId) {
        return kidRepository.findByParentId(parentId);
    }

    @PostMapping("/parent/{parentId}")
    public Kid create(@PathVariable Long parentId, @RequestBody Kid kid) {
        Parent parent = parentRepository.findById(parentId).orElseThrow();
        kid.setParent(parent);
        return kidRepository.save(kid);
    }
}

