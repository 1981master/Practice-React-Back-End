package com.master.practiceReact.controllers;

import com.master.practiceReact.Repository.ParentRepository;
import com.master.practiceReact.models.DTOs.ToDoDTO;
import com.master.practiceReact.models.Entity.Kid;
import com.master.practiceReact.models.Entity.Parent;
import com.master.practiceReact.service.KidService;
import com.master.practiceReact.service.ParentDetailsService;
import com.master.practiceReact.service.ToDoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/todo")
public class ToDoController {

    private static final Logger logger = LoggerFactory.getLogger(ToDoController.class);

    @Autowired
    private ToDoService toDoService;

    @Autowired
    private KidService kidService;
    private ParentDetailsService parentDetailsService;

    // -----------------------------
    // GET all todos for frontend
    // -----------------------------
    @GetMapping("/{kidId}")
    public ResponseEntity<List<ToDoDTO>> getTodos(@PathVariable Long kidId) {
        logger.info("[Todo] Fetching todos for kidId: {}", kidId);
        List<ToDoDTO> todos = toDoService.findByKidId(kidId);
        return ResponseEntity.ok(todos);
    }

    // -----------------------------
    // CREATE new todo
    // -----------------------------
    @PostMapping("/saveToDo")
    public ResponseEntity<ToDoDTO> createTodo(@RequestBody ToDoDTO todoDto) {
        try {
            logger.info("[Todo] Adding new todo: {}", todoDto.getText());

            Kid kid = kidService.findById(todoDto.getKidId());
            if (kid == null) {
                logger.info("Fatal saving todo because there is no Kid found with ID: {}", todoDto.getKidId());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            ToDoDTO saved = toDoService.save(todoDto, kid);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            logger.error("[Todo] Failed to add todo", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // -----------------------------
    // UPDATE todo (toggle completed, edit text/note/priority)
    // -----------------------------
    @PutMapping("/{id}")
    public ResponseEntity<ToDoDTO> updateTodo(@PathVariable Long id, @RequestBody ToDoDTO updatedDto) {
        try {
            logger.info("[Todo] Updating todo id: {}", id);
            ToDoDTO updated = toDoService.update(id, updatedDto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logger.error("[Todo] Failed to update todo id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // -----------------------------
    // DELETE todo
    // -----------------------------
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteTodo(@PathVariable Long id) {
        try {
            logger.info("[Todo] Deleting todo id: {}", id);
            toDoService.deleteById(id);
            return ResponseEntity.ok("Todo deleted successfully");
        } catch (Exception e) {
            logger.error("[Todo] Failed to delete todo id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete todo: " + e.getMessage());
        }
    }
}
