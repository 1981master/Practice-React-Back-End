package com.master.practiceReact.service;

import com.master.practiceReact.repository.KidRepository;
import com.master.practiceReact.repository.ParentRepository;
import com.master.practiceReact.models.DTOs.KidDTO;
import com.master.practiceReact.models.Entity.Kid;
import com.master.practiceReact.models.Entity.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KidService {

    private final KidRepository kidRepository;
    private final ParentRepository parentRepository;
    private final PasswordEncoder passwordEncoder;
    private final KidSetupService kidSetupService;
    @Autowired
    public KidService(KidRepository kidRepository,
                      ParentRepository parentRepository,
                      PasswordEncoder passwordEncoder, KidSetupService kidSetupService) {
        this.kidRepository = kidRepository;
        this.parentRepository = parentRepository;
        this.passwordEncoder = passwordEncoder;
        this.kidSetupService = kidSetupService;
    }

    @Transactional(readOnly = true)
    public List<KidDTO> getAllKids(String username) {
        Parent parent = (Parent) parentRepository.findByLoginId(username)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        List<Kid> kids = kidRepository.findByParentId(parent.getId());
        return kids.stream().map(kid -> {
            KidDTO dto = new KidDTO();
            dto.setId(kid.getId());
            dto.setName(kid.getName());
            dto.setAge(kid.getAge());
            dto.setGrade(kid.getGrade());
            dto.setChildLoginId(kid.getChildLoginId());
            dto.setCreatedAt(kid.getCreatedAt());
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public Kid addKid(Kid kid, String username) {
        Parent parent = (Parent) parentRepository.findByLoginId(username)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        kid.setParent(parent);

        // Ensure childLoginId is provided
        if (kid.getChildLoginId() == null || kid.getChildLoginId().isBlank()) {
            throw new RuntimeException("Child login ID is required");
        }

        // Set password = childLoginId (encoded)
        kid.setPassword(passwordEncoder.encode(kid.getChildLoginId()));

        return kidRepository.save(kid);
    }

    public Kid findById(Long kidId) {
        return kidRepository.findById(kidId)
                .orElseThrow(() -> new RuntimeException("Kid with provided Id not found"));
    }
    public Boolean existByLoginId(Long kidId){
        return kidRepository.existsById(kidId);
    }
    @Transactional
    public Kid registerKid(Kid kid) {
        // This will save the kid and assign default roles/permissions
        return kidSetupService.registerKid(kid);
    }

    public boolean existsByChildLoginId(String childLoginId) {
        return kidRepository.existsByChildLoginId(childLoginId);
    }
}
