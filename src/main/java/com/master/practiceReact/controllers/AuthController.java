package com.master.practiceReact.controllers;

import com.master.practiceReact.Repository.ParentRepository;
import com.master.practiceReact.Repository.RoleRepository;
import com.master.practiceReact.config.security.jwt.JwtUtil;
import com.master.practiceReact.models.DTOs.LoginRequest;
import com.master.practiceReact.models.DTOs.SignupRequest;
import com.master.practiceReact.models.DTOs.UserDTO;
import com.master.practiceReact.models.Entity.Parent;
import com.master.practiceReact.models.Entity.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final ParentRepository parentRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

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
            // Authenticate using Spring Security
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            Parent parent = parentRepo.findByEmail(request.getEmail()).orElseThrow();
            String token = jwtUtil.generateToken(parent.getEmail());

            UserDTO userDto = new UserDTO(parent); // include roles + permissions
            return ResponseEntity.ok(Map.of("token", token, "user", userDto));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        if (parentRepo.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        Parent parent = new Parent();
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
