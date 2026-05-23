package com.akhilraj.task_manager_api.controller;

import com.akhilraj.task_manager_api.service.TaskService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.akhilraj.task_manager_api.exception.TaskNotFoundException;
import com.akhilraj.task_manager_api.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService; 
    // creates a mocked service layer to isolate controller testing

    @Test
    void shouldReturn200WhenGettingTasks() throws Exception {
        // fails the test if checked exceptions occur during request execution

        Page<Task> emptyPage = new PageImpl<>(List.of());

        when(taskService.getPaginatedTasks(0, 5, "id", "asc"))
                .thenReturn(emptyPage);
        // mock service returns an empty paginated response

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk());
        // simulates GET /tasks request and expects HTTP 200 response
    }

    @Test
    void shouldReturn404WhenTaskDoesNotExist() throws Exception {

        when(taskService.getTaskById(999L))
                .thenThrow(new TaskNotFoundException(999L));

        mockMvc.perform(get("/tasks/999"))
                .andExpect(status().isNotFound());
    }
}