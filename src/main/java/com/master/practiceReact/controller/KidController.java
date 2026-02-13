package com.master.practiceReact.controller;

import com.master.practiceReact.models.DTOs.KidDTO;
import com.master.practiceReact.models.Entity.Kid;
import com.master.practiceReact.models.mappers.Mapper;
import com.master.practiceReact.service.KidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/kids")
public class KidController {

    private final KidService kidService;
    private static final Logger logger = LoggerFactory.getLogger(KidController.class);

    public KidController(KidService kidService) {
        this.kidService = kidService;
    }
    @PostMapping("/kids")
    public ResponseEntity<Kid> createKid(@RequestBody Kid kid) {
        Kid savedKid = kidService.registerKid(kid);
        return ResponseEntity.ok(savedKid);
    }
    @GetMapping
    public List<KidDTO> getAllKids(@AuthenticationPrincipal UserDetails userDetails) {
        logger.info("Request get all kids for user: {}", userDetails.getUsername());
        return kidService.getAllKids(userDetails.getUsername());
    }

    @PostMapping
    public KidDTO addKid(@RequestBody Kid kid,
                         @AuthenticationPrincipal UserDetails userDetails) {

        logger.info("Request add kid for user: {}", userDetails.getUsername());

        Kid savedKid = kidService.addKid(kid, userDetails.getUsername());
        return Mapper.toDTO(savedKid);
    }
}
