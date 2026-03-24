package com.akhilraj.task_manager_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskDTO {

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 3, message = "Title must be at least 3 characters")
    private String title;

    private boolean completed;

    public TaskDTO() {}

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}