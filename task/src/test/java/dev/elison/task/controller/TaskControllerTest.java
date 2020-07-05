package dev.elison.task.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import dev.elison.task.common.Task;
import dev.elison.task.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    public void getTasks() throws Exception {
        Mockito.when(taskService.getTasks()).thenReturn(List.of(new Task("Do Something")));

        mockMvc.perform(get("/task/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(taskService, times(1)).getTasks();
        verifyNoMoreInteractions(taskService);
    }

    @Test
    public void getTask() throws Exception {
        Task task = new Task("Make Unit Tests");
        task.setId(1L);
        Mockito.when(taskService.getTask(1L)).thenReturn(task);

        mockMvc.perform(get("/task/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Make Unit Tests"))
                .andExpect(jsonPath("$.complete").value(false));

        verify(taskService, times(1)).getTask(1L);
        verifyNoMoreInteractions(taskService);
    }

    @Test
    public void addTask() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        Task task = new Task("Clean");
        String requestJson = ow.writeValueAsString(task);

        mockMvc.perform(post("/task/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

        verify(taskService, times(1)).saveTask(task);
        verifyNoMoreInteractions(taskService);
    }

    @Test
    public void updateTask() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        Task task = new Task("Clean");
        task.setId(1L);
        String requestJson = ow.writeValueAsString(task);

        mockMvc.perform(put("/task/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

        verify(taskService, times(1)).saveTask(task);
        verifyNoMoreInteractions(taskService);
    }

    @Test
    public void deleteTask() throws Exception {
        mockMvc.perform(delete("/task/{id}", 1L))
                .andExpect(status().isOk());

        verify(taskService, times(1)).deleteTask(1L);
        verifyNoMoreInteractions(taskService);
    }
}