package com.akhilraj.task_manager_api.dto;

public class TaskResponseDTO {

    private Long id;
    private String title;
    private boolean completed;

    public TaskResponseDTO(Long id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
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
}