package com.master.practiceReact.service;

import com.master.practiceReact.Repository.KidRepository;
import com.master.practiceReact.Repository.ParentRepository;
import com.master.practiceReact.models.DTOs.KidDTO;
import com.master.practiceReact.models.Entity.Kid;
import com.master.practiceReact.models.Entity.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KidService {

    private final KidRepository kidRepository;
    private final ParentRepository parentRepository;

    @Autowired
    public KidService(KidRepository kidRepository, ParentRepository parentRepository) {
        this.kidRepository = kidRepository;
        this.parentRepository = parentRepository;
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
            dto.setCreatedAt(kid.getCreatedAt());
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public Kid addKid(Kid kid, String username) {
        Parent parent = (Parent) parentRepository.findByLoginId(username)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        kid.setParent(parent);
        return kidRepository.save(kid);
    }

    public Kid findById(Long kidId) {
        return kidRepository.findById(kidId).orElseThrow(()-> new RuntimeException("Kid with provided Id Not Found"));
    }
}
