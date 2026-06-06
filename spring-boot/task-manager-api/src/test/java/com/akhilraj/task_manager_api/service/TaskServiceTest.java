package com.akhilraj.task_manager_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.akhilraj.task_manager_api.repository.TaskRepository;
import java.util.Optional;

import com.akhilraj.task_manager_api.dto.TaskDTO;
import com.akhilraj.task_manager_api.exception.TaskNotFoundException;
import com.akhilraj.task_manager_api.model.Task;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldReturnTaskWhenTaskExists() {

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Learn Testing");

        when(repository.findById(1L))
                .thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Learn Testing", result.getTitle());
    }

    @Test
    void shouldThrowExceptionWhenTaskDoesNotExist() {
        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.getTaskById(1L)
        );
    }

    @Test
    void shouldDeleteTaskWhenTaskExists() {

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Learn Testing");

        when(repository.findById(1L))
                .thenReturn(Optional.of(task));

        taskService.deleteTask(1L);

        verify(repository).delete(task);
    }

    @Test
    void shouldUpdateTaskWhenTaskExists(){

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Old Title");
        task.setCompleted(false);
        TaskDTO dto = new TaskDTO();
        dto.setTitle("Updated Task");
        dto.setCompleted(true);

        when(repository.findById(1L))
                .thenReturn(Optional.of(task));

        when(repository.save(any(Task.class)))
                .thenReturn(task);
        Task result = taskService.updateTask(1L, dto);

        assertEquals("Updated Task", result.getTitle());
        assertTrue(result.isCompleted());
        verify(repository).save(any(Task.class));

    }

    @Test
    void shouldCreateTaskSuccessfully(){
        TaskDTO dto = new TaskDTO();
        dto.setTitle("Learn Mockito");
        dto.setCompleted(false);

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle("Learn Mockito");
        savedTask.setCompleted(false);

        when(repository.save(any(Task.class)))
        .thenReturn(savedTask);

        Task result = taskService.addTask(dto);

        assertEquals(1L, result.getId());
        assertEquals("Learn Mockito", result.getTitle());

        verify(repository).save(any(Task.class));
    }

    }

