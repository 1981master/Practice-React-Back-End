package com.master.PracticeReact.Entity;

import com.master.PracticeReact.enums.Priority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private String note;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
    private Priority priority;
    private Boolean completed;
    private Boolean archived = Boolean.FALSE;


    @PrePersist
    public void onCreate(){
        createdAt = LocalDateTime.now();
    }
    public void onUpdate(){
        updatedAt = LocalDateTime.now();
        if(completed && completedAt == null){
            completedAt = LocalDateTime.now();
        }
    }
}
