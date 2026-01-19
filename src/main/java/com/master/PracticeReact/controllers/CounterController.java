package com.master.PracticeReact.controllers;

import com.master.PracticeReact.DTOs.CounterDTO;
import com.master.PracticeReact.Entity.Counter;
import com.master.PracticeReact.service.CounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/counter")
public class CounterController {

    private static final Logger logger =
            LoggerFactory.getLogger(CounterController.class);

    private final CounterService counterService;

    public CounterController(CounterService counterService) {
        this.counterService = counterService;
    }

    @GetMapping("/allCounters")
    public ResponseEntity<?> getAllCounters() {
        var counters = counterService.getAllCounters();

        // Map to DTOs
        var dtoList = counters.stream()
                .map(c -> new CounterDTO(c.getId(), c.getCounter()))
                .toList();

        return ResponseEntity.ok(dtoList);
    }
}