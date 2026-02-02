package com.master.practiceReact.Repository;

import com.master.practiceReact.models.Entity.Kid;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface KidRepository extends JpaRepository<Kid, Long> {
    List<Kid> findByParentId(Long parentId);
}

