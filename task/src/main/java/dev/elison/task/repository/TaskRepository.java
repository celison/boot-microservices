package dev.elison.task.repository;

import dev.elison.task.common.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {

}
