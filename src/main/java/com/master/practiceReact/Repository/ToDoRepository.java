package com.master.practiceReact.Repository;

import com.master.practiceReact.models.DTOs.ToDoDTO;
import com.master.practiceReact.models.Entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    // Fetch todos by kid ID (Long)
    List<ToDo> findByKidId(Long kidId);

    // Fetch todos by kid's login (String)
    @Query("SELECT t FROM ToDo t WHERE t.kid.childLoginId = :loginId AND t.archived = false")
    List<ToDo> findByKidLogin(@Param("loginId") String loginId);

    // Fetch todo by id and parent's login
    @Query("SELECT t FROM ToDo t WHERE t.id = :id AND t.parent.loginId = :loginId")
    Optional<ToDo> findByIdAndParentLogin(@Param("id") Long id, @Param("loginId") String loginId);
}

