package com.master.practiceReact.Repository;

import com.master.practiceReact.models.Entity.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    List<Attempt> findBySessionId(Long sessionId);
}

