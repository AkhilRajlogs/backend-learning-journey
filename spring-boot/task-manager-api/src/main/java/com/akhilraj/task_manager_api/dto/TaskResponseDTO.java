package com.akhilraj.task_manager_api.dto;

import java.time.LocalDateTime;

public class TaskResponseDTO {

    private Long id;
    private String title;
    private boolean completed;
    private LocalDateTime createdAt;

    public TaskResponseDTO(Long id, String title, boolean completed, LocalDateTime createdAt){

        this.id = id;
        this.title = title;
        this.completed = completed;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDateTime getCreatedAt() {
    return createdAt;
    }
}