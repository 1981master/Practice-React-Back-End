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
import java.util.List;
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
                          ParentSetupService parentSetupService,
                          KidRepository kidRepo) {
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
            logger.info("User Type is trying to log in: {}", request.getUserType());
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
                // Authenticate parent using Spring Security
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginIdentifier, request.getPassword())
                );

                Parent parent = (Parent) parentRepo.findByLoginIdOrEmail(loginIdentifier, loginIdentifier)
                        .orElseThrow(() -> new RuntimeException("Parent not found"));

                String token = jwtUtil.generateToken(parent.getLoginId(), Map.of("type", "PARENT"));
                UserDTO userDto = new UserDTO(parent);

                return ResponseEntity.ok(Map.of("token", token, "user", userDto));

            } else if (userType.equals("KID")) {
                // Find kid by childLoginId
                Kid kid = (Kid) kidRepo.findByChildLoginId(loginIdentifier)
                        .orElseThrow(() -> new RuntimeException("Kid not found"));

                // ✅ Verify kid's password
                if (!passwordEncoder.matches(request.getPassword(), kid.getPassword())) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
                }

                String token = jwtUtil.generateToken(
                        kid.getChildLoginId(),
                        Map.of("type", "KID", "kidId", kid.getId())
                );

                // Collect all permissions for the kid
                List<String> permissions = kid.getRoles().stream()
                        .flatMap(role -> role.getPermissions().stream())
                        .map(p -> p.getName())
                        .distinct() // optional, remove duplicates
                        .toList();

                Map<String, Object> kidDto = Map.of(
                        "id", kid.getId(),
                        "name", kid.getName(),
                        "childLoginId", kid.getChildLoginId(),
                        "permissions", List.of("VIEW_TODOS", "VIEW_TOPICS")// hard code it for now, permissions // <-- include the permissions here
                );


                return ResponseEntity.ok(Map.of("token", token, "user", kidDto));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user type");
            }

        } catch (Exception e) {
            logger.error("Login failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        // Check if loginId already exists
        if (parentRepo.existsByLoginId(request.getLoginId())) {
            logger.warn("Parent ID '{}' already exists.", request.getLoginId());
            return ResponseEntity.badRequest().body("Parent ID already exists");
        }

        // Create new Parent entity
        Parent parent = new Parent();
        parent.setLoginId(request.getLoginId());
        parent.setEmail(request.getEmail() != null ? request.getEmail() : "");
        parent.setPassword(passwordEncoder.encode(request.getPassword()));

        logger.info("Saving new parent with Parent ID: {}", parent.getLoginId());
        parentRepo.save(parent);

        // Assign roles and permissions
        logger.info("Assigning roles and permissions to parent: {}", parent.getLoginId());
        parentSetupService.registerFirstParent(parent);

        // Force eager loading to avoid LazyInitializationException
        parent.getRoles().forEach(role -> role.getPermissions().size());

        String token = jwtUtil.generateToken(parent.getLoginId(), new HashMap<>());
        UserDTO userDto = new UserDTO(parent);

        return ResponseEntity.ok(Map.of("token", token, "user", userDto));
    }
}
