package com.akhilraj.task_manager_api.controller;

import com.akhilraj.task_manager_api.dto.TaskDTO;
import com.akhilraj.task_manager_api.dto.TaskResponseDTO;
import com.akhilraj.task_manager_api.model.Task;
import com.akhilraj.task_manager_api.service.TaskService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponseDTO> getTasks() {
        return taskService.getAllTasks()
                .stream()
                .map(taskService::mapToResponseDTO)
                .toList();
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody @Valid TaskDTO taskDTO) {
        Task task = taskService.addTask(taskDTO);
        return ResponseEntity.status(201).body(taskService.mapToResponseDTO(task));
    }


    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);

        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(taskService.mapToResponseDTO(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);

        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskDTO taskDTO) {

        Task task = taskService.updateTask(id, taskDTO);

        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(taskService.mapToResponseDTO(task));
    }
    
}