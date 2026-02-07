package com.master.practiceReact.Repository;

import com.master.practiceReact.models.Entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    Optional<Parent> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Parent> findByLoginId(String identifier);

    Optional<Object> findByLoginIdOrEmail(String identifier, String identifier1);

    boolean existsByLoginId(String loginId);
}
