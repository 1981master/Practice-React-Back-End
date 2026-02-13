package com.master.practiceReact.repository;

import com.master.practiceReact.models.Entity.Subject;
import com.master.practiceReact.models.Entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findBySubjectId(Long subjectId);
    Subject findByName(String name);

}

