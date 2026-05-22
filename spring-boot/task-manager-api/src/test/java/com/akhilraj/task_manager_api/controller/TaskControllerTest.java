package com.akhilraj.task_manager_api.controller;

import com.akhilraj.task_manager_api.service.TaskService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

    @Test
    void shouldReturn200WhenGettingTasks() throws Exception {

        Page emptyPage = new PageImpl(List.of());

        when(taskService.getPaginatedTasks(0, 5, "id", "asc"))
                .thenReturn(emptyPage);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk());
    }
}