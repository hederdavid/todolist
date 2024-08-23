package com.todolist.todo_app.services;

import com.todolist.todo_app.domain.task.CreateTaskDTO;
import com.todolist.todo_app.domain.task.Task;
import com.todolist.todo_app.domain.user.User;
import com.todolist.todo_app.exceptions.ResourceNotFoundException;
import com.todolist.todo_app.repositories.TaskRepository;
import com.todolist.todo_app.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Task> createTask(@Valid CreateTaskDTO task) {
        Optional<User> optionalUser = userRepository.findById(task.userId());
        User user = optionalUser.orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Task taskEntity = new Task(task, user);

        taskEntity = taskRepository.save(taskEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskEntity);
    }
}
