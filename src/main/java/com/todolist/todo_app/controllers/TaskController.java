package com.todolist.todo_app.controllers;

import com.todolist.todo_app.domain.task.CreateTaskDTO;
import com.todolist.todo_app.domain.task.TaskDetails;
import com.todolist.todo_app.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<List<TaskDetails>> getUserTodoTasks(@PathVariable UUID id) {
        return taskService.getAllTasksTodo(id);
    }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<TaskDetails> create(@RequestBody @Valid CreateTaskDTO task) {
        return taskService.createTask(task);
    }
}
