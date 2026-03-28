package com.akhilraj.task_manager_api.controller;

import com.akhilraj.task_manager_api.dto.ApiConstants;
import com.akhilraj.task_manager_api.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<TaskResponseDTO>>> getTasks() {

        List<TaskResponseDTO> tasks = taskService.getAllTasks()
                .stream()
                .map(taskService::mapToResponseDTO)
                .toList();

        ApiResponse<List<TaskResponseDTO>> response =
                new ApiResponse<>(ApiConstants.SUCCESS, "Tasks fetched successfully", tasks);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponseDTO>> createTask(
            @RequestBody @Valid TaskDTO taskDTO) {

        Task task = taskService.addTask(taskDTO);
        TaskResponseDTO dto = taskService.mapToResponseDTO(task);

        ApiResponse<TaskResponseDTO> response =
                new ApiResponse<>(ApiConstants.SUCCESS, "Task created successfully", dto);

        return ResponseEntity.status(201).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> getTaskById(@PathVariable Long id) {

        Task task = taskService.getTaskById(id);
        TaskResponseDTO dto = taskService.mapToResponseDTO(task);

        ApiResponse<TaskResponseDTO> response =
                new ApiResponse<>(ApiConstants.SUCCESS, "Task fetched successfully", dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);

        ApiResponse<Void> response =
                new ApiResponse<>(ApiConstants.SUCCESS, "Task deleted successfully", null);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskDTO taskDTO) {

        Task task = taskService.updateTask(id, taskDTO);
        TaskResponseDTO dto = taskService.mapToResponseDTO(task);

        ApiResponse<TaskResponseDTO> response =
                new ApiResponse<>(ApiConstants.SUCCESS, "Task updated successfully", dto);

        return ResponseEntity.ok(response);
    }
    
}