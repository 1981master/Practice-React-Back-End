package com.master.practiceReact.repository;

import com.master.practiceReact.models.Entity.Counter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CounterRepository extends JpaRepository<Counter, Long> {

    // Get all counters for a specific kid
    List<Counter> findByKidId(Long kidId);

    // Optional: find global counters (kid = null)
    List<Counter> findByKidIsNull();

    // Optional: find by type + kid
    Counter findByTypeAndKidId(com.master.practiceReact.models.enums.CounterType type, Long kidId);
}
