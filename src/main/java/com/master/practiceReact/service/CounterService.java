package com.master.practiceReact.service;

import com.master.practiceReact.models.Entity.Counter;
import com.master.practiceReact.Repository.CounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CounterService {

    @Autowired
    private CounterRepository counterRepository;

    /** Get all counters (global + per kid) **/
    public List<Counter> getAllCounters() {
        return counterRepository.findAll();
    }

    /** Get counters for a specific kid **/
    public List<Counter> getCountersByKid(Long kidId) {
        return counterRepository.findByKidId(kidId);
    }

    /** Optional: Get global counters only (kid = null) **/
    public List<Counter> getGlobalCounters() {
        return counterRepository.findByKidIsNull();
    }

    /** Optional: Find specific counter by type & kid **/
    public Counter getCounterByTypeAndKid(com.master.practiceReact.models.enums.CounterType type, Long kidId) {
        return counterRepository.findByTypeAndKidId(type, kidId);
    }
}
