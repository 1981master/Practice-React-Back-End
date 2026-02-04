package com.master.practiceReact.Repository;

import com.master.practiceReact.models.Entity.LearningSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LearningSessionRepository extends JpaRepository<LearningSession, Long> {

    List<LearningSession> findByKidId(Long kidId);

    List<LearningSession> findByKidIdAndStartTimeBetween(
            Long kidId,
            LocalDateTime start,
            LocalDateTime end
    );
}

