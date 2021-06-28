package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskMapperTestSuite {

    @Autowired
    TaskMapper taskMapper;

    @Test
    public void testMapToTask() {
        //given
        TaskDto taskDto = new TaskDto(1L, "title", "content");

        //when
        Task task = taskMapper.mapToTask(taskDto);

        //then
        assertEquals(1L, task.getId());
        assertEquals("title", task.getTitle());
        assertEquals("content", task.getContent());
    }

    @Test
    public void testMapToTaskDto() {
        //given
        Task task = new Task (1L, "title", "content");

        //when
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //then
        assertEquals(1L, taskDto.getId());
        assertEquals("title", taskDto.getTitle());
        assertEquals("content", taskDto.getContent());
    }

    @Test
    public void testMapToTaskDtoList() {
        //given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task (1L, "title", "content"));

        //when
        List<TaskDto> dtoList = taskMapper.mapToTaskDtoList(taskList);

        //then
        assertEquals(1, dtoList.size());
        assertEquals(1L, dtoList.get(0).getId());
        assertEquals("title", dtoList.get(0).getTitle());
        assertEquals("content", dtoList.get(0).getContent());
    }
}
