package org.example.skillboxtasktracker.mapper;

import org.example.skillboxtasktracker.controller.request.UserRequest;
import org.example.skillboxtasktracker.controller.response.UserResponse;
import org.example.skillboxtasktracker.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "password")
    User toEntity(UserRequest userRequest, String password);

    UserResponse toResponse(User user);
}