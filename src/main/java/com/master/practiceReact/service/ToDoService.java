package com.master.practiceReact.service;

import com.master.practiceReact.Repository.KidRepository;
import com.master.practiceReact.Repository.ParentRepository;
import com.master.practiceReact.Repository.ToDoRepository;
import com.master.practiceReact.models.DTOs.ToDoDTO;
import com.master.practiceReact.models.Entity.Parent;
import com.master.practiceReact.models.Entity.ToDo;
import com.master.practiceReact.models.Entity.Kid;
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

    @Autowired
    private ParentRepository parentRepo;

    @Autowired
    private KidRepository kidRepo;

    // ========================
    // Fetch all ToDos (for admin/parent)
    // ========================
    public List<ToDoDTO> findAllToDo() {
        return toDoRepository.findAll()
                .stream()
                .map(Mapper::toDTO)
                .collect(Collectors.toList());
    }

    // ========================
    // Fetch ToDo by ID
    // ========================
    public ToDoDTO findToDoById(Long id) {
        ToDo todo = toDoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
        return Mapper.toDTO(todo);
    }

    // ========================
    // Save new ToDo (assign to parent + kid)
    // ========================
    public ToDoDTO save(ToDoDTO dto, Parent parent, Kid kid) {
        if (!kid.getParent().getId().equals(parent.getId())) {
            throw new RuntimeException("Cannot assign ToDo to a kid not belonging to you");
        }

        ToDo todo = new ToDo();
        todo.setText(dto.getText());
        todo.setNote(dto.getNote());
        todo.setPriority(dto.getPriority());
        todo.setKid(kid);
        todo.setParent(parent);
        todo.setCompleted(false);
        todo.setArchived(false);

        return Mapper.toDTO(toDoRepository.save(todo));
    }

    // ========================
    // Update existing ToDo
    // ========================
    public ToDoDTO update(Long id, ToDoDTO updatedDto) {
        ToDo todo = toDoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        // Update fields safely
        if (updatedDto.getText() != null) todo.setText(updatedDto.getText());
        if (updatedDto.getNote() != null) todo.setNote(updatedDto.getNote());
        if (updatedDto.getPriority() != null) todo.setPriority(updatedDto.getPriority());

        // Handle completed toggle
        boolean prevCompleted = todo.isCompleted();  // entity
        boolean updatedCompleted = updatedDto.getCompleted(); // DTO, use getCompleted()
        todo.setCompleted(updatedCompleted);

        if (!prevCompleted && updatedCompleted) {
            todo.setCompletedAt(LocalDateTime.now());
        } else if (prevCompleted && !updatedCompleted) {
            todo.setCompletedAt(null);
        }

        return Mapper.toDTO(toDoRepository.save(todo));
    }


    // ========================
    // Delete ToDo
    // ========================
    public void deleteById(Long id, Parent parent) {
        ToDo todo = toDoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        if (!todo.getParent().getId().equals(parent.getId())) {
            throw new RuntimeException("Cannot delete ToDo that does not belong to you");
        }

        toDoRepository.delete(todo);
    }

    // ========================
    // Fetch todos by kidId
    // ========================
    public List<ToDoDTO> findByKidId(Long kidId) {
        return toDoRepository.findByKidId(kidId)
                .stream()
                .map(Mapper::toDTO)
                .collect(Collectors.toList());
    }

    // ========================
    // Archive ToDo
    // ========================
    public ToDoDTO archiveToDo(Long id, Parent parent) {
        ToDo todo = toDoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        if (!todo.getParent().getId().equals(parent.getId())) {
            throw new RuntimeException("Cannot archive ToDo that does not belong to you");
        }

        todo.setArchived(true);
        return Mapper.toDTO(toDoRepository.save(todo));
    }

    // ========================
    // Update with authorization (Parent or Kid)
    // ========================
    public ToDoDTO updateWithAuthorization(Long id, ToDoDTO updatedDto, String loginId, boolean isParent) {
        ToDo todo = toDoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        if (isParent) {
            // Fetch parent by loginId
            Parent parent = parentRepo.findByLoginId(loginId)
                    .orElseThrow(() -> new RuntimeException("Parent not found"));

            if (!todo.getParent().getId().equals(parent.getId())) {
                throw new RuntimeException("Parent not authorized to update this todo");
            }
        } else {
            // Must be the kid assigned to this todo
            Kid kid = kidRepo.findByChildLoginId(loginId)
                    .orElseThrow(() -> new RuntimeException("Kid not found"));

            if (!todo.getKid().getId().equals(kid.getId())) {
                throw new RuntimeException("Kid not authorized to update this todo");
            }
        }

        // Update fields
        if (updatedDto.getText() != null) todo.setText(updatedDto.getText());
        if (updatedDto.getNote() != null) todo.setNote(updatedDto.getNote());
        if (updatedDto.getPriority() != null) todo.setPriority(updatedDto.getPriority());

        // Handle completed toggle
        boolean prevCompleted = todo.isCompleted();           // ✅ use isCompleted()
        todo.setCompleted(updatedDto.getCompleted());         // ✅ use isCompleted() from DTO
        if (!prevCompleted && updatedDto.getCompleted()) {
            todo.setCompletedAt(LocalDateTime.now());
        } else if (prevCompleted && !updatedDto.getCompleted()) {
            todo.setCompletedAt(null);
        }

        ToDo saved = toDoRepository.save(todo);
        return Mapper.toDTO(saved);
    }

    // Delete a todo by ID (parent only)
    public void delete(Long id, String username) {
        ToDo todo = toDoRepository.findByIdAndParentLogin(id, username)
                .orElseThrow(() -> new RuntimeException("Parent not authorized to delete this todo"));

        toDoRepository.delete(todo);
    }

    // Fetch todos for kid by login
    public List<ToDoDTO> findByKidLogin(String username) {
        return toDoRepository.findByKidLogin(username)   // Use the @Query method
                .stream()
                .map(Mapper::toDTO)
                .collect(Collectors.toList());
    }

    // Assign a new ToDo to a kid by parent
    public ToDoDTO assign(ToDoDTO dto, String parentLogin) {

        // Fetch parent by login
        Parent parent = parentRepo.findByLoginId(parentLogin)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        // Fetch kid by id from DTO
        Kid kid = kidRepo.findById(dto.getKidId())
                .orElseThrow(() -> new RuntimeException("Kid not found"));

        // Ensure kid belongs to parent
        if (!kid.getParent().getId().equals(parent.getId())) {
            throw new RuntimeException("Cannot assign ToDo to a kid not belonging to you");
        }

        // Create ToDo entity
        ToDo todo = new ToDo();
        todo.setParent(parent);
        todo.setKid(kid);
        todo.setText(dto.getText());
        todo.setNote(dto.getNote());
        todo.setPriority(dto.getPriority());
        todo.setCompleted(false);
        todo.setArchived(false);

        // Save and return DTO
        ToDo saved = toDoRepository.save(todo);
        return Mapper.toDTO(saved);
    }

}
