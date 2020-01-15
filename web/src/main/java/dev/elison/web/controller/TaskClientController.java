package dev.elison.web.controller;

import dev.elison.task.common.Task;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/v1/task")
public class TaskClientController {

    private static final String BASE_URL = "http://task-service/task/";

    private final RestTemplate restTemplate;

    public TaskClientController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public List<Task> getTasks() {
        ResponseEntity<List<Task>> tasks = restTemplate.exchange(BASE_URL, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        return tasks.getBody();
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        ResponseEntity<Task> task = restTemplate.exchange(BASE_URL + id, HttpMethod.GET, null, Task.class);
        return task.getBody();
    }

    @PostMapping("/")
    public void addTask(@RequestBody Task task) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        restTemplate.exchange(BASE_URL, HttpMethod.POST, new HttpEntity<>(task, httpHeaders), Void.class);
    }

    @PutMapping("/")
    public void updateTask(@RequestBody Task task) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        restTemplate.exchange(BASE_URL, HttpMethod.PUT, new HttpEntity<>(task, httpHeaders), Void.class);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        restTemplate.exchange(BASE_URL + id, HttpMethod.DELETE, null, Void.class);
    }
}
