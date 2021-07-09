package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class TaskController {

    private final DbService dbService;
    private final TaskMapper taskMapper;

    @GetMapping(value = "tasks")
    public List<TaskDto> getTasks() {
        List<Task> tasks = dbService.getAllTasks();
        return taskMapper.mapToTaskDtoList(tasks);
    }
    @GetMapping(value = "tasks/{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) throws TaskNotFoundException{
        return taskMapper.mapToTaskDto(
                dbService.getTaskById(taskId).orElseThrow(TaskNotFoundException::new)
        );
    }

    @DeleteMapping(value = "tasks/{taskId}") public void deleteTask (@PathVariable Long taskId) throws TaskNotFoundException {
        if (dbService.getTaskById(taskId).isPresent()) {
            dbService.deleteTaskById(taskId);
        } else throw new TaskNotFoundException();
    }

    @PutMapping(value = "tasks")
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        Task savedTask = dbService.saveTask(task);
        return taskMapper.mapToTaskDto(savedTask);
    }

    @PostMapping(value = "tasks", consumes = MediaType.APPLICATION_JSON_VALUE) public void createTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        dbService.saveTask(task);
    }
}
