package com.master.practiceReact.service;

import com.master.practiceReact.Repository.ToDoRepository;
import com.master.practiceReact.models.DTOs.ToDoDTO;
import com.master.practiceReact.models.Entity.ToDo;
import com.master.practiceReact.models.Entity.Kid;
import com.master.practiceReact.models.mappers.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToDoService {

    @Autowired
    private ToDoRepository toDoRepository;

    // Fetch all ToDos
    public List<ToDoDTO> findAllToDo() {
        return toDoRepository.findAll()
                .stream()
                .map(Mapper::toDTO)
                .collect(Collectors.toList());
    }

    // Fetch ToDo by ID
    public ToDoDTO findToDoById(Long id) {
        ToDo todo = toDoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fatal fetching todo with id:" + id));
        return Mapper.toDTO(todo);
    }

    // Save or update ToDo
    public ToDoDTO save(ToDoDTO toDoDTO, Kid kid) {
        ToDo todo = Mapper.fromDTO(toDoDTO, kid);
        ToDo saved = toDoRepository.save(todo);
        return Mapper.toDTO(saved);
    }

    // Delete ToDo
    public void deleteById(Long id) {
        toDoRepository.deleteById(id);
    }

    // Optional: fetch todos by kid
    public List<ToDoDTO> findByKidId(Long kidId) {
        return toDoRepository.findByKidId(kidId)
                .stream()
                .map(Mapper::toDTO)
                .collect(Collectors.toList());
    }
}
