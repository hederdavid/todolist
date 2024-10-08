package com.todolist.todo_app.controllers;

import com.todolist.todo_app.domain.task.CreateTaskDTO;
import com.todolist.todo_app.domain.task.TaskDetailsDTO;
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
    public ResponseEntity<List<TaskDetailsDTO>> getUserTodoTasks(@PathVariable UUID id) {
        return taskService.getAllTasksTodo(id);
    }

    @GetMapping("/completed/{id}")
    public ResponseEntity<List<TaskDetailsDTO>> getUserCompletedTasks(@PathVariable UUID id) {
        return taskService.getAllTasksCompleted(id);
    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<TaskDetailsDTO> create(@RequestBody @Valid CreateTaskDTO task) {
        return taskService.createTask(task);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return taskService.deleteTask(id);
    }

    @PutMapping("/update/{id}")
    @Transactional
    public ResponseEntity<TaskDetailsDTO> update (@PathVariable UUID id, @RequestBody @Valid CreateTaskDTO task) {
        return taskService.updateTask(id, task);
    }



}
