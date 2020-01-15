package dev.elison.task;

import dev.elison.task.common.Task;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaskApplicationTest {

    public TaskApplicationTest(){}

    @Test
    public void contextLoads() {
    }

    @Test
    public void taskLoads() {
        Task task = new Task("A Task");
    }
}