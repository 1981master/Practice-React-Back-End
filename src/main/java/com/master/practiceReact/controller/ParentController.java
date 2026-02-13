package com.master.practiceReact.controller;

import com.master.practiceReact.repository.ParentRepository;
import com.master.practiceReact.models.Entity.Parent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/parents")
public class ParentController {

    private final ParentRepository parentRepository;

    public ParentController(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    @GetMapping
    public List<Parent> getAll() {
        return parentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parent> getById(@PathVariable Long id) {
        return parentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Parent> create(@RequestBody Parent parent) {
        // TODO: hash password before saving
        Parent saved = parentRepository.save(parent);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parent> update(@PathVariable Long id, @RequestBody Parent parent) {
        return parentRepository.findById(id)
                .map(existing -> {
                    existing.setEmail(parent.getEmail());
                    existing.setPassword(parent.getPassword()); // TODO: hash password
                    parentRepository.save(existing);
                    return ResponseEntity.ok(existing);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!parentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        parentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
