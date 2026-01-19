package com.master.PracticeReact.DTOs;

import com.master.PracticeReact.enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDoDTO {
    private Long id;
    private String text;
    private String note;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
    private Priority priority;
    private Boolean completed;
    private Boolean archived = Boolean.FALSE;

}
