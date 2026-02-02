package com.master.practiceReact.controllers;

import com.master.practiceReact.models.DTOs.ToDoDTO;
import com.master.practiceReact.models.Entity.Kid;
import com.master.practiceReact.service.KidService;
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
@RequestMapping("/todo")
public class ToDoController {

    Logger logger = LoggerFactory.getLogger(ToDoController.class);
    @Autowired
    ToDoService toDoService;
    @Autowired
    KidService kidService;
    @GetMapping("/allToDo")
    public List<com.master.practiceReact.models.DTOs.ToDoDTO> getToDos(){
        return toDoService.findAllToDo();
    }

    @PostMapping("/saveToDo")
    public ToDoDTO insertToDo(@RequestBody com.master.practiceReact.models.DTOs.ToDoDTO todo) {
        // Fetch the Kid entity using the kidId from the DTO
        Kid kid = kidService.findById(todo.getKidId()); // You need a KidService that fetches Kid by ID
        return toDoService.save(todo, kid);
    }


    @DeleteMapping("/deleteToDo/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteToDo(@PathVariable("id") Long todoDto) {
        try {
            logger.info("ToDoDTO Text: {}",todoDto);
            toDoService.deleteById(todoDto);
//            toDoService.delete(toDoService.mapDTOToDo(todoDto));
            return ResponseEntity.ok("Todo deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to delete todo: " + e.getMessage());
        }
    }


}
