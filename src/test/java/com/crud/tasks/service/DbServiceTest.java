package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    void createTask() {
        //given
        Task task = new Task(null, "title", "content");
        Task savedTask = new Task(1L, "title", "content");

        when(taskRepository.save(task)).thenReturn(savedTask);

        //when
        Task newTask = dbService.saveTask(task);

        //
        assertEquals(1L, newTask.getId());
    }

    @Test
    void testGetById() throws IOException {
        //given
        Task savedTask = new Task(1L, "title", "content");
        Optional<Task> optionalTask = Optional.of(savedTask);

        when(taskRepository.findById(savedTask.getId())).thenReturn(optionalTask);

        //when
        Task retrievedTask = dbService.getTaskById(savedTask.getId()).orElseThrow(IOException::new);

        //then
        assertEquals("title", retrievedTask.getTitle());
        assertEquals("content", retrievedTask.getContent());
    }

    @Test
    void testGetAllTasks() {
        //given
        List<Task> taskList = List.of(new Task(1L, "title", "content"));

        when(taskRepository.findAll()).thenReturn(taskList);

        //when
        List<Task> list = dbService.getAllTasks();

        //then
        assertEquals(1, list.size());
    }

    @Test
    void testDeleteById() {
        //given
        Task task = new Task(1L, "title", "content");

        doNothing().when(taskRepository).deleteById(task.getId());

        //when
        dbService.deleteTaskById(task.getId());

        //then
        verify(taskRepository, times(1)).deleteById(task.getId());
    }
}
