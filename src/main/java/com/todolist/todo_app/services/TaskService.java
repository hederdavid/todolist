package com.todolist.todo_app.services;

import com.todolist.todo_app.domain.task.CreateTaskDTO;
import com.todolist.todo_app.domain.task.Task;
import com.todolist.todo_app.domain.task.TaskDetails;
import com.todolist.todo_app.domain.user.User;
import com.todolist.todo_app.exceptions.ResourceNotFoundException;
import com.todolist.todo_app.repositories.TaskRepository;
import com.todolist.todo_app.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<TaskDetails> createTask(@Valid CreateTaskDTO task) {
        Optional<User> optionalUser = userRepository.findById(task.userId());
        User user = optionalUser.orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Task taskEntity = new Task(task, user);
        taskEntity = taskRepository.save(taskEntity);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(taskEntity.getId())
                .toUri();

        return ResponseEntity.created(location).body(new TaskDetails(taskEntity));
    }

    public ResponseEntity<List<TaskDetails>> getAllTasksTodo(UUID userId) {
        List<Task> tasks = taskRepository.findByUserIdAndCompletedFalse(userId);

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<TaskDetails> tasksDetails = tasks.stream()
                .map(TaskDetails::new)
                .toList();

        return ResponseEntity.ok(tasksDetails);
    }

}
