package com.master.practiceReact.controller;

import com.master.practiceReact.models.DTOs.KidDTO;
import com.master.practiceReact.models.Entity.Kid;
import com.master.practiceReact.models.Entity.Parent;
import com.master.practiceReact.models.mappers.Mapper;
import com.master.practiceReact.service.KidService;
import com.master.practiceReact.service.ParentDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/kids")
public class KidController {

    private final KidService kidService;
    private final ParentDetailsService parentDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(KidController.class);

    public KidController(KidService kidService, ParentDetailsService parentDetailsService) {
        this.kidService = kidService;
        this.parentDetailsService = parentDetailsService;
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
    public ResponseEntity<?> addKid(@RequestBody Kid kid,
                                    @AuthenticationPrincipal UserDetails userDetails) {

        // 1️⃣ Validate input first
        if (kid == null || kid.getChildLoginId() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "status", 400,
                            "error", "Bad Request",
                            "message", "Kid or Child LoginID must not be null"
                    ));
        }

        String childLoginId;
        try {
            childLoginId = kid.getChildLoginId();
            if(kidService.existsByChildLoginId(childLoginId)){
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Map.of(
                                "status", 409,
                                "error", "Conflict",
                                "message", "Kid with same LoginID already exists"
                        ));
            }
        } catch (NumberFormatException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "status", 400,
                            "error", "Bad Request",
                            "message", "Child LoginID must be a number"
                    ));
        }

        // 2️⃣ Check if parent exists
        if (Optional.ofNullable(parentDetailsService.findById(Long.valueOf(childLoginId))).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "status", 409,
                            "error", "Conflict",
                            "message", "Parent with same LoginID already exists"
                    ));
        }

        // 3️⃣ Add the kid safely
        Kid savedKid = kidService.addKid(kid, userDetails.getUsername());

        // 4️⃣ Return DTO
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Mapper.toDTO(savedKid));
    }




}
