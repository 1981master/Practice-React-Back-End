package com.master.practiceReact.service;

import com.master.practiceReact.Repository.ToDoRepository;
import com.master.practiceReact.models.DTOs.ToDoDTO;
import com.master.practiceReact.models.Entity.ToDo;
import com.master.practiceReact.models.Entity.Kid;
import com.master.practiceReact.models.enums.Priority;
import com.master.practiceReact.models.mappers.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    // Save new ToDo
    public ToDoDTO save(ToDoDTO toDoDTO, Kid kid) {
        ToDo todo = Mapper.fromDTO(toDoDTO, kid);
        if (todo.getPriority() == null) {
            todo.setPriority(Priority.MEDIUM); // default if not set
        }
        ToDo saved = toDoRepository.save(todo);
        return Mapper.toDTO(saved);
    }

    // Update existing ToDo
    public ToDoDTO update(Long id, ToDoDTO updatedDto) {
        ToDo todo = toDoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        // Update fields
        if (updatedDto.getText() != null) todo.setText(updatedDto.getText());
        todo.setNote(updatedDto.getNote());
        if (updatedDto.getPriority() != null) todo.setPriority(updatedDto.getPriority());

        // Handle completed toggle
        boolean prevCompleted = todo.getCompleted();
        todo.setCompleted(updatedDto.getCompleted());
        if (!prevCompleted && updatedDto.getCompleted()) {
            todo.setCompletedAt(LocalDateTime.now());
        } else if (prevCompleted && !updatedDto.getCompleted()) {
            todo.setCompletedAt(null);
        }

        ToDo saved = toDoRepository.save(todo);
        return Mapper.toDTO(saved);
    }

    // Delete ToDo
    public void deleteById(Long id) {
        toDoRepository.deleteById(id);
    }

    // Fetch todos by kidId
    public List<ToDoDTO> findByKidId(Long kidId) {
        return toDoRepository.findByKidId(kidId)
                .stream()
                .map(Mapper::toDTO)
                .collect(Collectors.toList());
    }
}
