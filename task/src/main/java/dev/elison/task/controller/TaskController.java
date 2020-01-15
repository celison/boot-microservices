package dev.elison.task.controller;

import dev.elison.task.common.Task;
import dev.elison.task.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/")
    public Iterable<Task> getTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @PostMapping("/")
    public void addTask(@RequestBody Task task) {
        taskRepository.save(task);
    }

    @PutMapping("/")
    public void updateTask(@RequestBody Task task) {
        taskRepository.save(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
}
