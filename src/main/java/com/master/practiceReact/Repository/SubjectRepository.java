package com.master.practiceReact.Repository;

import com.master.practiceReact.models.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}

