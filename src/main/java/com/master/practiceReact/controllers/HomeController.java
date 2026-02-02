package com.master.practiceReact.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
This constructor mainly now for H2 access on console. later on this.
 */
@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "PracticeReact backend is running!";
    }
}
