package org.example.skillboxtasktracker.mapper;

import org.example.skillboxtasktracker.controller.request.TaskRequest;
import org.example.skillboxtasktracker.controller.response.TaskResponse;
import org.example.skillboxtasktracker.entity.Task;
import org.example.skillboxtasktracker.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(uses = UserMapper.class)
public interface TaskMapper {
    @Mapping(target = "observers", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "assignee", ignore = true)
    @Mapping(target = "authorId", source = "user.id")
    Task toEntity(TaskRequest taskRequest, Instant createdAt, Instant updatedAt, User user);

    TaskResponse toResponse(Task task);
}