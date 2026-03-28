package com.akhilraj.task_manager_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.akhilraj.task_manager_api.dto.ApiConstants;
import com.akhilraj.task_manager_api.dto.ApiResponse;
import com.akhilraj.task_manager_api.exception.TaskNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        logger.error("Validation failed: {}", errors);

        ApiResponse<Map<String, String>> response =
                new ApiResponse<>(ApiConstants.ERROR, "Validation failed", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleTaskNotFound(TaskNotFoundException ex) {

        logger.error("Task not found exception: {}", ex.getMessage());

        ApiResponse<Void> response =
                new ApiResponse<>(ApiConstants.ERROR, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}