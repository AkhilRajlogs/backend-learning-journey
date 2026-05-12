package com.akhilraj.task_manager_api.dto;

import java.time.LocalDateTime;

public class TaskResponseDTO {

    private Long id;
    private String title;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TaskResponseDTO(Long id, String title, boolean completed, LocalDateTime createdAt, LocalDateTime updatedAt){

        this.id = id;
        this.title = title;
        this.completed = completed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}