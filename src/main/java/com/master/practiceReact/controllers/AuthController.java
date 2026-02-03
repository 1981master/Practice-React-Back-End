package com.master.practiceReact.controllers;

import com.master.practiceReact.Repository.ParentRepository;
import com.master.practiceReact.Repository.RoleRepository;
import com.master.practiceReact.config.security.jwt.JwtUtil;
import com.master.practiceReact.models.DTOs.LoginRequest;
import com.master.practiceReact.models.DTOs.SignupRequest;
import com.master.practiceReact.models.DTOs.UserDTO;
import com.master.practiceReact.models.Entity.Parent;
import com.master.practiceReact.models.Entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final ParentRepository parentRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    public AuthController(AuthenticationManager authManager,
                          JwtUtil jwtUtil,
                          ParentRepository parentRepo,
                          RoleRepository roleRepo,
                          PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.parentRepo = parentRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            logger.info("Login Request for ParentId: {}", request.getParentId());
            // Determine login identifier: prefer Parent ID
            if(request.getEmail() == null || request.getEmail().isEmpty()){
                request.setEmail("");
            }
            String loginIdentifier = request.getParentId() != null && !request.getParentId().isBlank()
                    ? request.getParentId()
                    : request.getEmail();

            if (loginIdentifier == null || loginIdentifier.isBlank()) {
                logger.error("Fatal Login due to not provide  Email: {}, and ParentId/or: {}", request.getEmail(), request.getParentId());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parent ID or Email is required");
            }

            // Authenticate using Spring Security (must match UserDetailsService)
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginIdentifier, request.getPassword())
            );

            // Fetch Parent by loginId (primary) or email (fallback)
            Parent parent = (Parent) parentRepo.findByLoginIdOrEmail(loginIdentifier, loginIdentifier)
                    .orElseThrow(() -> new RuntimeException("Parent not found"));

            // Generate JWT token using Parent ID
            String token = jwtUtil.generateToken(parent.getLoginId());

            // Map parent to DTO including roles & permissions
            UserDTO userDto = new UserDTO(parent);

            return ResponseEntity.ok(Map.of("token", token, "user", userDto));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        if (parentRepo.existsByLoginId(request.getLoginId())) {
            return ResponseEntity.badRequest().body("Parent ID already exists");
        }
        logger.info("this is this Signup ID: {}", request.getLoginId());
        if(request.getEmail() == null || request.getEmail().isEmpty()){
            request.setEmail("");
        }
        Parent parent = new Parent();
        parent.setLoginId(request.getLoginId());
        parent.setEmail(request.getEmail());
        parent.setPassword(passwordEncoder.encode(request.getPassword()));
        parentRepo.save(parent);

        // First user becomes ADMIN
        if (parentRepo.count() == 1) {
            Role adminRole = (Role) roleRepo.findByName("ADMIN")
                    .orElseGet(() -> roleRepo.save(new Role("ADMIN")));
            parent.getRoles().add(adminRole);
            parentRepo.save(parent);
        }

        // Generate JWT immediately after signup
        String token = jwtUtil.generateToken(parent.getEmail());
        UserDTO userDto = new UserDTO(parent);

        return ResponseEntity.ok(Map.of("token", token, "user", userDto));
    }
}
