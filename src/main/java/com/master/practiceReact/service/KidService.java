package com.master.practiceReact.service;

import com.master.practiceReact.Repository.KidRepository;
import com.master.practiceReact.models.Entity.Kid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KidService {

    @Autowired
    private KidRepository kidRepository;

    public Kid findById(Long id) {
        return kidRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kid not found with id: " + id));
    }
}
