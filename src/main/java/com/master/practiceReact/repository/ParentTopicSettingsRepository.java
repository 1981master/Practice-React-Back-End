package com.master.practiceReact.repository;

import com.master.practiceReact.models.Entity.ParentTopicSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ParentTopicSettingsRepository extends JpaRepository<ParentTopicSettings, Long> {
    Optional<ParentTopicSettings> findByParentIdAndKidIdAndTopicId(Long parentId, Long kidId, Long topicId);
}
