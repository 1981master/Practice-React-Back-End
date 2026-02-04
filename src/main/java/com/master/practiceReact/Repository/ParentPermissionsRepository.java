package com.master.practiceReact.Repository;

import com.master.practiceReact.models.Entity.ParentPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentPermissionsRepository extends JpaRepository<ParentPermission, Long> {
}
