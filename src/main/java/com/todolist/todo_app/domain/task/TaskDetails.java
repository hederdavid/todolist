package com.todolist.todo_app.domain.task;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDetails(
        String title,
        String description,
        LocalDateTime createdAt,
        LocalDateTime dueDate,
        boolean completed,
        UUID userId

) {
    public TaskDetails(Task taskEntity) {
        this(
                taskEntity.getTitle(),
                taskEntity.getDescription(),
                taskEntity.getCreatedAt(),
                taskEntity.getDueDate(),
                taskEntity.isCompleted(),
                taskEntity.getUser().getId()
        );
    }
}
