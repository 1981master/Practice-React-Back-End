package com.master.practiceReact.repository;

import com.master.practiceReact.models.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    <T> Optional<T> findByName(String admin);

    boolean existsByName(String admin);
}
