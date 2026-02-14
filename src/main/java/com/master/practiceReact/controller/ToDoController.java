package com.master.practiceReact.controller;

import com.master.practiceReact.models.DTOs.ToDoDTO;
import com.master.practiceReact.service.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> assign(
            @RequestBody ToDoDTO dto,
            Authentication authentication) {

        String username = authentication.getName();

        ToDoDTO assignedTodo = toDoService.assign(dto, username);
        if (assignedTodo == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "status", 400,
                            "error", "Bad Request",
                            "message", "No Parent or Child found with provided id: " + dto.getKidId()
                    ));
        }
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

        ToDoDTO updatedTodo = toDoService.updateWithAuthorization(id, dto, username, isParent);

        return ResponseEntity.ok(updatedTodo);
    }

    // ========================
    // Delete todo (parent only)
    // ========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            Authentication authentication) {

        boolean isParent = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_USER"));

        if (!isParent) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        toDoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ========================
    // Fetch todos
    // ========================
//    @GetMapping
//    public ResponseEntity<List<ToDoDTO>> getTodos(Authentication authentication) {
//
//        String username = authentication.getName();
//
//        boolean isParent = authentication.getAuthorities()
//                .stream()
//                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_USER"));
//
//        List<ToDoDTO> todos;
//
//        if (isParent) {
//            // Parent: fetch all todos
//            todos = toDoService.findByParentUsername(username);
//        } else {
//            // Kid: fetch only their todos
//            todos = toDoService.findByKidLogin(username);
//        }
//
//        return ResponseEntity.ok(todos);
//    }
    @GetMapping
    public ResponseEntity<List<ToDoDTO>> getTodos(Authentication authentication) {

        // Get current logged-in username
        String username = authentication.getName();

        // Check if user is parent/admin based on roles
        boolean isParent = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")); // only admin = parent

        List<ToDoDTO> todos;

        if (isParent) {
            // Parent: fetch all todos
            todos = toDoService.findByParentUsername(username);
        } else {
            // Kid: fetch only their todos
            todos = toDoService.findByKidLogin(username);
        }

        return ResponseEntity.ok(todos);
    }

}
