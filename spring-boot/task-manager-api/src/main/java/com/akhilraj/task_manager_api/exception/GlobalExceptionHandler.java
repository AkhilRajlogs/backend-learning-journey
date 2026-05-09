package com.akhilraj.task_manager_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidJson(HttpMessageNotReadableException ex) {

        logger.error("Malformed JSON request: {}", ex.getMessage());

        ApiResponse<Void> response =
                new ApiResponse<>(ApiConstants.ERROR, "Malformed JSON request", null);

        return ResponseEntity.badRequest().body(response);
    }

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

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException ex) {

        logger.error("Unsupported media type: {}", ex.getMessage());

        ApiResponse<Void> response =
                new ApiResponse<>(ApiConstants.ERROR, "Content-Type not supported", null);

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {

        logger.error("Unexpected error occurred", ex);

        ApiResponse<Void> response =
                new ApiResponse<>(
                        ApiConstants.ERROR,
                        "Internal server error",
                        null
                );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}