package com.todolist.todo_app.services;

import com.todolist.todo_app.domain.task.CreateTaskDTO;
import com.todolist.todo_app.domain.task.Task;
import com.todolist.todo_app.domain.task.TaskDetailsDTO;
import com.todolist.todo_app.domain.user.User;
import com.todolist.todo_app.exceptions.ResourceNotFoundException;
import com.todolist.todo_app.repositories.TaskRepository;
import com.todolist.todo_app.repositories.UserRepository;
import jakarta.validation.Valid;
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

    public ResponseEntity<TaskDetailsDTO> createTask(@Valid CreateTaskDTO task) {
        Optional<User> optionalUser = userRepository.findById(task.userId());
        User user = optionalUser.orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Task taskEntity = new Task(task, user);
        taskEntity = taskRepository.save(taskEntity);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(taskEntity.getId())
                .toUri();

        return ResponseEntity.created(location).body(new TaskDetailsDTO(taskEntity));
    }

    public ResponseEntity<List<TaskDetailsDTO>> getAllTasksTodo(UUID userId) {
        List<Task> tasks = taskRepository.findByUserIdAndCompletedFalse(userId);

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<TaskDetailsDTO> tasksDetails = tasks.stream()
                .map(TaskDetailsDTO::new)
                .toList();

        return ResponseEntity.ok(tasksDetails);
    }

    public ResponseEntity<List<TaskDetailsDTO>> getAllTasksCompleted(UUID userId) {
        List<Task> tasks = taskRepository.findByUserIdAndCompletedTrue(userId);

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<TaskDetailsDTO> tasksDetails = tasks.stream()
                .map(TaskDetailsDTO::new)
                .toList();

        return ResponseEntity.ok(tasksDetails);

    }

    public ResponseEntity<Void> deleteTask(UUID id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        taskRepository.delete(optionalTask.orElseThrow(() -> new ResourceNotFoundException("Task not found")));

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<TaskDetailsDTO> updateTask(UUID id, @Valid CreateTaskDTO task) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            optionalTask.get().setTitle(task.title());
            optionalTask.get().setDescription(task.description());
            optionalTask.get().setDueDate(task.dueDate());
            return ResponseEntity.ok(new TaskDetailsDTO(optionalTask.get()));
        }
        throw new ResourceNotFoundException("Task not found");
    }
}
