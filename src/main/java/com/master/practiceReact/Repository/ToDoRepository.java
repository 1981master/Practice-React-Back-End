package com.master.practiceReact.Repository;

import com.master.practiceReact.models.Entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    // Optional: get all todos for a specific kid
    List<ToDo> findByKidId(Long kidId);
}
