package com.master.PracticeReact.Repository;

import com.master.PracticeReact.Entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
}
