package com.master.practiceReact.Repository;

import com.master.practiceReact.models.DTOs.ToDoDTO;
import com.master.practiceReact.models.Entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    List<ToDo> findByKidId(Long kidId);
    List<ToDo> findByKidIdAndArchivedFalse(Long kidId);

}
