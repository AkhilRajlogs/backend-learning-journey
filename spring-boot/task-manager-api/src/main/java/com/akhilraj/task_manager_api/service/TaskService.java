package com.akhilraj.task_manager_api.service;

import com.akhilraj.task_manager_api.repository.TaskRepository;
import com.akhilraj.task_manager_api.dto.TaskDTO;
import com.akhilraj.task_manager_api.dto.TaskResponseDTO;
import com.akhilraj.task_manager_api.model.Task;
import org.springframework.stereotype.Service;
import com.akhilraj.task_manager_api.exception.TaskNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        logger.info("Fetching all tasks");
        return taskRepository.findAll();
    }

    public Task addTask(TaskDTO dto) {
        logger.info("Creating new task with title: {}", dto.getTitle());

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setCompleted(dto.isCompleted());

        Task savedTask = taskRepository.save(task);

        logger.info("Task created with id: {}", savedTask.getId());

        return savedTask;
    }

    public Task getTaskById(Long id) {
        logger.info("Fetching task with id: {}", id);

        return taskRepository.findById(id)
                .orElseThrow(() -> {
                    
                    return new TaskNotFoundException(id);
                });
    }

    public void deleteTask(Long id) {
        logger.info("Deleting task with id: {}", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskRepository.delete(task);

        logger.info("Task deleted successfully: {}", id);
    }

    public Task updateTask(Long id, TaskDTO dto) {
        logger.info("Updating task with id: {}", id);

        return taskRepository.findById(id).map(task -> {
            task.setTitle(dto.getTitle());
            task.setCompleted(dto.isCompleted());

            logger.info("Task updated successfully: {}", id);

            return taskRepository.save(task);
        }).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public TaskResponseDTO mapToResponseDTO(Task task) {
    return new TaskResponseDTO(
            task.getId(),
            task.getTitle(),
            task.isCompleted()
        );
    }
}