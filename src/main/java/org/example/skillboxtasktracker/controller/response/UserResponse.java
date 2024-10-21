package org.example.skillboxtasktracker.controller.response;

import org.example.skillboxtasktracker.entity.RoleType;

import java.util.Set;

public record UserResponse(
        String id,
        String username,
        String email,
        Set<RoleType> roles) {
}
