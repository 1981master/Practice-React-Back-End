package com.master.practiceReact.repository;

import com.master.practiceReact.models.Entity.ParentPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentPermissionsRepository extends JpaRepository<ParentPermission, Long> {
}
