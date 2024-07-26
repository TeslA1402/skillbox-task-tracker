package org.example.skillboxtasktracker.controller.response;

import org.example.skillboxtasktracker.entity.TaskStatus;

import java.time.Instant;
import java.util.Set;

public record TaskResponse(
        String id,
        String name,
        String description,
        Instant createdAt,
        Instant updatedAt,
        TaskStatus status,
        UserResponse author,
        UserResponse assignee,
        Set<UserResponse> observers) {
}