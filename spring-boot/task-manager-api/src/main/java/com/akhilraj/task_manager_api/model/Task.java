package com.akhilraj.task_manager_api.model;

public class Task {

    private int id;
    private String title;
    private boolean completed;

    // Default constructor (VERY IMPORTANT)
    public Task() {}

    public Task(int id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {   // 🔥 THIS WAS MISSING
        this.title = title;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}