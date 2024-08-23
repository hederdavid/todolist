package com.todolist.todo_app.domain.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateTaskDTO(

        @NotBlank
        String title,

        String description,

        LocalDateTime dueDate,

        @NotNull
        UUID userId

) {
}
