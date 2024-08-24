package com.todolist.todo_app.repositories;

import com.todolist.todo_app.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByUserIdAndCompletedFalse(UUID userId);
}
