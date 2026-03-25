package com.akhilraj.task_manager_api.service;

import com.akhilraj.task_manager_api.repository.TaskRepository;
import com.akhilraj.task_manager_api.dto.TaskDTO;
import com.akhilraj.task_manager_api.dto.TaskResponseDTO;
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

    public Task addTask(TaskDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setCompleted(dto.isCompleted());

        return taskRepository.save(task);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Task updateTask(Long id, TaskDTO dto) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(dto.getTitle());
            task.setCompleted(dto.isCompleted());
            return taskRepository.save(task);
        }).orElse(null);
    }

    public TaskResponseDTO mapToResponseDTO(Task task) {
    return new TaskResponseDTO(
            task.getId(),
            task.getTitle(),
            task.isCompleted()
        );
    }
}