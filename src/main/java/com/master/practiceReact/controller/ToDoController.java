package com.master.practiceReact.controller;

import com.master.practiceReact.models.DTOs.ToDoDTO;
import com.master.practiceReact.service.ToDoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
@CrossOrigin(origins = "http://localhost:3000")
public class ToDoController {

    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    // ========================
    // Parent assigns a todo
    // ========================
    @PostMapping("/assign")
    public ResponseEntity<ToDoDTO> assign(
            @RequestBody ToDoDTO dto,
            Authentication authentication) {

        String username = authentication.getName();

        // Call service to assign todo with parent authorization
        ToDoDTO assignedTodo = toDoService.assign(dto, username);

        return ResponseEntity.ok(assignedTodo);
    }

    // ========================
    // Update todo (parent or kid)
    // ========================
    @PutMapping("/{id}")
    public ResponseEntity<ToDoDTO> update(
            @PathVariable Long id,
            @RequestBody ToDoDTO dto,
            Authentication authentication) {

        String username = authentication.getName();

        boolean isParent = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_USER"));

        // Call service with proper authorization handling
        ToDoDTO updatedTodo = toDoService.updateWithAuthorization(id, dto, username, isParent);

        return ResponseEntity.ok(updatedTodo);
    }

    // ========================
    // Delete todo (parent only)
    // ========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        toDoService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // ========================
    // Optional: kid fetch own todos
    // ========================
    @GetMapping("/my-todos")
    public ResponseEntity<List<ToDoDTO>> myTodos(Authentication authentication) {

        String username = authentication.getName();

        List<ToDoDTO> todos = toDoService.findByKidLogin(username);

        return ResponseEntity.ok(todos);
    }
}
