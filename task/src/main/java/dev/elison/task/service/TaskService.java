package dev.elison.task.service;

import dev.elison.task.common.Task;

public interface TaskService {
    Iterable<Task> getTasks();

    Task getTask(Long id);

    void saveTask(Task task);

    void deleteTask(Long id);
}
