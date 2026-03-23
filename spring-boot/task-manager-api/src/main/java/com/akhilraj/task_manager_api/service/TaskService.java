package com.akhilraj.task_manager_api.service;

import com.akhilraj.task_manager_api.repository.TaskRepository;
import com.akhilraj.task_manager_api.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public Task getTaskById(int id) {
    return taskRepository.findById(id).orElse(null);
    }

    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }

    public Task updateTask(int id, Task updatedTask) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setCompleted(updatedTask.isCompleted());
            return taskRepository.save(task);
        }).orElse(null);
    }
}