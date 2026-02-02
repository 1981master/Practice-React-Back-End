package com.master.practiceReact.models.mappers;

import com.master.practiceReact.models.DTOs.ToDoDTO;
import com.master.practiceReact.models.Entity.ToDo;
import com.master.practiceReact.models.Entity.Kid;

public class Mapper {

    // -------------------- ToDo mapping --------------------
    public static ToDoDTO toDTO(ToDo todo) {
        if (todo == null) return null;

        ToDoDTO dto = new ToDoDTO();
        dto.setId(todo.getId());
        dto.setText(todo.getText());
        dto.setNote(todo.getNote());
        dto.setPriority(todo.getPriority());
        dto.setCompleted(todo.getCompleted());
        dto.setArchived(todo.getArchived());
        dto.setCreatedAt(todo.getCreatedAt());
        dto.setUpdatedAt(todo.getUpdatedAt());
        dto.setCompletedAt(todo.getCompletedAt());

        if (todo.getKid() != null) {
            dto.setKidId(todo.getKid().getId());
        }

        return dto;
    }

    public static ToDo fromDTO(ToDoDTO dto, Kid kid) {
        if (dto == null) return null;

        ToDo todo = new ToDo();
        todo.setId(dto.getId());
        todo.setText(dto.getText());
        todo.setNote(dto.getNote());
        todo.setPriority(dto.getPriority());
        todo.setCompleted(dto.getCompleted());
        todo.setArchived(dto.getArchived());
        todo.setCreatedAt(dto.getCreatedAt());
        todo.setUpdatedAt(dto.getUpdatedAt());
        todo.setCompletedAt(dto.getCompletedAt());
        todo.setKid(kid);

        return todo;
    }

    // -------------------- Kid mapping (optional) --------------------
    // Example if you have a KidDTO
    /*
    public static KidDTO toDTO(Kid kid) { ... }
    public static Kid fromDTO(KidDTO dto, Parent parent) { ... }
    */
}
