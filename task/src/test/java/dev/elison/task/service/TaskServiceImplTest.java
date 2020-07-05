package dev.elison.task.service;

import dev.elison.task.common.Task;
import dev.elison.task.repository.TaskRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class TaskServiceImplTest {

    @MockBean
    private TaskRepository taskRepository;
    private TaskService taskService;

    @Before
    public void setUp() {
        if (taskService == null) {
            taskService = new TaskServiceImpl(taskRepository);
        }
    }

    @Test
    public void getTasks() {
        when(taskRepository.findAll()).thenReturn(Set.of(new Task("A"), new Task("B")));

        assertThat(StreamSupport.stream(taskService.getTasks().spliterator(), false).count()).isEqualTo(2L);

        verify(taskRepository, times(1)).findAll();
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void getTask() {
    }

    @Test
    public void saveTask() {
    }

    @Test
    public void deleteTask() {
    }
}