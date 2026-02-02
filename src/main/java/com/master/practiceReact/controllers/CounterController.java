package com.master.practiceReact.controllers;

import com.master.practiceReact.models.DTOs.CounterDTO;
import com.master.practiceReact.models.Entity.Counter;
import com.master.practiceReact.service.CounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/counter")
public class CounterController {

    private static final Logger logger = LoggerFactory.getLogger(CounterController.class);

    private final CounterService counterService;

    public CounterController(CounterService counterService) {
        this.counterService = counterService;
    }

    /** GET all counters (global and per kid) **/
    @GetMapping("/all")
    public ResponseEntity<List<CounterDTO>> getAllCounters() {
        List<Counter> counters = counterService.getAllCounters();

        // Map to DTO
        List<CounterDTO> dtoList = counters.stream()
                .map(c -> new CounterDTO(
                        c.getId(),
                        c.getType(), // type is now String
                        c.getKid() != null ? c.getKid().getId() : null,
                        c.getCount(),
                        c.getLastUpdated()
                ))
                .toList();

        return ResponseEntity.ok(dtoList);
    }

    /** GET counters by kid **/
    @GetMapping("/kid/{kidId}")
    public ResponseEntity<List<CounterDTO>> getCountersByKid(@PathVariable Long kidId) {
        List<Counter> counters = counterService.getCountersByKid(kidId);

        List<CounterDTO> dtoList = counters.stream()
                .map(c -> new CounterDTO(
                        c.getId(),
                        c.getType(), // type is now String
                        c.getKid() != null ? c.getKid().getId() : null,
                        c.getCount(),
                        c.getLastUpdated()
                ))
                .toList();

        return ResponseEntity.ok(dtoList);
    }
}
