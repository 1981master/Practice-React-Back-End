package com.master.PracticeReact.controllers;

import com.master.PracticeReact.DTOs.ToDoDTO;
import com.master.PracticeReact.Entity.ToDo;
import com.master.PracticeReact.service.ToDoService;
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
    @GetMapping("/allToDo")
    public List<ToDoDTO> getToDos(){
        return toDoService.findAllToDo();
    }

    @PostMapping("/saveToDo")
    public ToDo insertToDo(@RequestBody ToDoDTO todo){
        return toDoService.save(todo);
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
