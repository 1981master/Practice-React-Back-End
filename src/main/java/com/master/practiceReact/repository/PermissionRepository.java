package com.master.practiceReact.repository;

import com.master.practiceReact.models.Entity.Permission;
import org.apache.juli.logging.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Log> {
    Optional<Permission> findByName(String permName);
}
