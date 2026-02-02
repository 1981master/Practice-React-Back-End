package com.master.practiceReact.Repository;

import com.master.practiceReact.models.Entity.TopicProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TopicProgressRepository extends JpaRepository<TopicProgress, Long> {
    List<TopicProgress> findByKidId(Long kidId);
}
