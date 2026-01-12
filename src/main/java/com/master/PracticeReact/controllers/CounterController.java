package com.master.PracticeReact.controllers;

import com.master.PracticeReact.service.CounterService;
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

    @Autowired
    private CounterService counterService;

    @RequestMapping("/allCounters")
    @GetMapping
    public ResponseEntity<?> getAllCounters(){
        return
                ResponseEntity.ok(counterService.getAllCounters());

    }
}
