package com.master.practiceReact.models.mappers;

import com.master.practiceReact.models.DTOs.KidDTO;
import com.master.practiceReact.models.DTOs.ParentDTO;
import com.master.practiceReact.models.DTOs.ToDoDTO;
import com.master.practiceReact.models.Entity.Parent;
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

    public static ParentDTO toDTO(Parent parent) {
        ParentDTO dto = new ParentDTO();
        dto.setId(parent.getId());
        dto.setLoginId(parent.getLoginId());
        dto.setEmail(parent.getEmail());
        dto.setCreatedAt(parent.getCreatedAt());
        dto.setRoles(
                parent.getRoles()
                        .stream()
                        .map(r -> r.getName())
                        .collect(java.util.stream.Collectors.toSet())
        );
        return dto;
    }

    // -------------------- Kid mapping (optional) --------------------
    // Example if you have a KidDTO

    public static KidDTO toDTO(Kid kid) {
        KidDTO kidDTO = new KidDTO();
        kidDTO.setId(kid.getId());
        kidDTO.setName(kid.getName());
        kidDTO.setAge(kid.getAge());
        kidDTO.setGrade(kid.getGrade());
        kidDTO.setCreatedAt(kid.getCreatedAt());

        return kidDTO;
    }
    public static Kid fromDTO(KidDTO dto, Parent parent) {
        Kid kid = new Kid();
        kid.setId(dto.getId());
        kid.setParent(parent);
        kid.setName(dto.getName());
        kid.setCreatedAt(dto.getCreatedAt());
        kid.setGrade(dto.getGrade());
        kid.setAge(dto.getAge());
       return kid;
    }

}
