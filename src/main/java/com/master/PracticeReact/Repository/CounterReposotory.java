package com.master.PracticeReact.Repository;

import com.master.PracticeReact.Entity.Counter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CounterReposotory extends JpaRepository<Counter, Long> {
}
