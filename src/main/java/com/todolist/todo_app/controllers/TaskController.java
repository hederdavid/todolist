package com.todolist.todo_app.controllers;

import com.todolist.todo_app.domain.task.CreateTaskDTO;
import com.todolist.todo_app.domain.task.Task;
import com.todolist.todo_app.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<Task> create(@RequestBody @Valid CreateTaskDTO task) {
        System.out.println("entrou");
        return taskService.createTask(task);
    }
}
