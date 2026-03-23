package com.akhilraj.task_manager_api.service;

import com.akhilraj.task_manager_api.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private List<Task> tasks = new ArrayList<>();

    public TaskService() {
        tasks.add(new Task("Learn Spring", false));
        tasks.add(new Task("Build API", false));
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Task addTask(Task task) {
        tasks.add(task);
        return task;
    }

    public Task getTaskById(int id) {
    return tasks.stream()
            .filter(task -> task.getId() == id)
            .findFirst()
            .orElse(null);
    }

    public void deleteTask(int id) {
        tasks.removeIf(task -> task.getId() == id);
    }

    public Task updateTask(int id, Task updatedTask) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setTitle(updatedTask.getTitle());
                task.setCompleted(updatedTask.isCompleted());
                return task;
            }
        }
        return null;
    }
}