package com.master.PracticeReact.service;

import com.master.PracticeReact.DTOs.ToDoDTO;
import com.master.PracticeReact.Entity.ToDo;
import com.master.PracticeReact.Repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToDoService {
    @Autowired
    ToDoRepository toDoRepository;

    public List<ToDoDTO> findAllToDo() {
        List<ToDo> todos = toDoRepository.findAll();
        List<ToDoDTO> toDoDTOS = todos.stream()
                .map((todo) -> {
                    return this.mapToDo(todo);
                })
                .collect(Collectors.toList());
        return toDoDTOS;
    }

    public ToDo findToDoById(Long id){
        return toDoRepository.findById(id).orElseThrow(() -> new RuntimeException("Fatal fetching todo with id:"+ id));
    }

    public ToDoDTO mapToDo(ToDo todo){

        ToDoDTO to = new ToDoDTO();
        to.setId(todo.getId());
        to.setArchived(todo.getArchived());
        to.setNote(todo.getNote());
        to.setText(todo.getText());
        to.setPriority(todo.getPriority());
        to.setCompleted(todo.getCompleted());
        to.setCompletedAt(todo.getCompletedAt());
        to.setUpdatedAt(todo.getUpdatedAt());
        return to;
    }

    public ToDo mapDTOToDo(ToDoDTO todo){

        ToDo to = new ToDo();
        to.setId(todo.getId());
        to.setArchived(todo.getArchived());
        to.setNote(todo.getNote());
        to.setText(todo.getText());
        to.setPriority(todo.getPriority());
        to.setCompleted(todo.getCompleted());
        to.setCompletedAt(todo.getCompletedAt());
        to.setUpdatedAt(todo.getUpdatedAt());
        return to;
    }

    public ToDo save(ToDoDTO toDoDTO) {
        return toDoRepository.save(this.mapDTOToDo(toDoDTO));
    }

    public void deleteById(Long todo){
         toDoRepository.deleteById(todo);
    }
}
