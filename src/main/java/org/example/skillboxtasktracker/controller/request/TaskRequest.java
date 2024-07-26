package org.example.skillboxtasktracker.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.skillboxtasktracker.entity.TaskStatus;

import java.util.Set;

public record TaskRequest(
        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotNull
        TaskStatus status,
        @NotBlank
        String authorId,
        @NotBlank
        String assigneeId,
        Set<@NotBlank String> observerIds
) {
}