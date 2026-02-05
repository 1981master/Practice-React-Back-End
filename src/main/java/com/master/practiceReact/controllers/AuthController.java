package com.master.practiceReact.controllers;

import com.master.practiceReact.Repository.KidRepository;
import com.master.practiceReact.Repository.ParentRepository;
import com.master.practiceReact.Repository.RoleRepository;
import com.master.practiceReact.config.security.jwt.JwtUtil;
import com.master.practiceReact.models.DTOs.LoginRequest;
import com.master.practiceReact.models.DTOs.SignupRequest;
import com.master.practiceReact.models.DTOs.UserDTO;
import com.master.practiceReact.models.Entity.Kid;
import com.master.practiceReact.models.Entity.Parent;
import com.master.practiceReact.service.ParentSetupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    private final ParentSetupService parentSetupService;
    private final KidRepository kidRepo;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthenticationManager authManager,
                          JwtUtil jwtUtil,
                          ParentRepository parentRepo,
                          RoleRepository roleRepo,
                          PasswordEncoder passwordEncoder,
                          ParentSetupService parentSetupService, KidRepository kidRepo) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.parentRepo = parentRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.parentSetupService = parentSetupService;
        this.kidRepo = kidRepo;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // Determine login identifier (ParentID or Email)
            String loginIdentifier = request.getParentId() != null && !request.getParentId().isBlank()
                    ? request.getParentId()
                    : (request.getEmail() != null ? request.getEmail() : "");

            if (loginIdentifier.isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Login ID or Email is required");
            }

            // Determine user type (Parent or Kid)
            String userType = request.getUserType() != null ? request.getUserType().toUpperCase() : "PARENT";

            if (userType.equals("PARENT")) {
                // Authenticate parent normally
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginIdentifier, request.getPassword())
                );

                // Find parent by login ID or email
                Parent parent = (Parent) parentRepo.findByLoginIdOrEmail(loginIdentifier, loginIdentifier)
                        .orElseThrow(() -> new RuntimeException("Parent not found"));

                // Generate token with "PARENT" as a claim
                String token = jwtUtil.generateToken(parent.getLoginId(), Map.of("type", "PARENT"));

                // Create UserDTO for the parent (exclude sensitive information)
                UserDTO userDto = new UserDTO(parent);

                // Return token and user data
                return ResponseEntity.ok(Map.of("token", token, "user", userDto));

            } else if (userType.equals("KID")) {
                // Kids login — no password required
                Kid kid = (Kid) kidRepo.findByChildLoginId(loginIdentifier)
                        .orElseThrow(() -> new RuntimeException("Kid not found"));

                // Generate token with "KID" as a claim (including kid ID)
                String token = jwtUtil.generateToken(kid.getChildLoginId(), Map.of("type", "KID", "kidId", kid.getId()));

                // Send minimal info about the kid (you can extend this based on your requirements)
                Map<String, Object> kidDto = Map.of(
                        "id", kid.getId(),
                        "name", kid.getName(),
                        "childLoginId", kid.getChildLoginId()
                );

                // Return token and kid info
                return ResponseEntity.ok(Map.of("token", token, "user", kidDto));

            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user type");
            }

        } catch (Exception e) {
            // Return unauthorized error for failed login attempts
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }





    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        // Step 1: Check if the loginId already exists
        if (parentRepo.existsByLoginId(request.getLoginId())) {
            logger.warn("Parent ID '{}' already exists.", request.getLoginId());
            return ResponseEntity.badRequest().body("Parent ID already exists");
        }

        // Step 2: Create a new Parent entity and set the login credentials
        Parent parent = new Parent();
        parent.setLoginId(request.getLoginId());
        parent.setEmail(request.getEmail() != null ? request.getEmail() : "");
        parent.setPassword(passwordEncoder.encode(request.getPassword()));

        // Step 3: Save the new parent to the repository (this will store the parent record)
        logger.info("Saving new parent with Parent ID: {}", parent.getLoginId());
        parentRepo.save(parent);

        // Step 4: Assign roles and permissions for the first parent
        logger.info("Assigning roles and permissions to parent: {}", parent.getLoginId());
        parentSetupService.registerFirstParent(parent); // This should grant ALL roles and permissions to the parent

        // Step 5: Force eager loading of roles and permissions to avoid LazyInitializationException
        parent.getRoles().forEach(role -> role.getPermissions().size()); // Ensures roles/permissions are loaded before returning the JWT
        logger.debug("Roles and permissions eagerly loaded for parent: {}", parent.getLoginId());

        // Step 6: Generate JWT token
        String token = jwtUtil.generateToken(parent.getLoginId(), new HashMap<>());
        logger.info("JWT token generated for parent: {}", parent.getLoginId());

        // Step 7: Convert Parent to UserDTO to return only necessary user data
        UserDTO userDto = new UserDTO(parent);
        logger.info("Converted parent to UserDTO for Parent ID: {}", parent.getLoginId());

        // Step 8: Return the JWT token and user data
        logger.info("Returning response with token and user data for Parent ID: {}", parent.getLoginId());
        return ResponseEntity.ok(Map.of("token", token, "user", userDto));
    }
}
