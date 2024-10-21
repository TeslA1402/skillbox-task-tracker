package org.example.skillboxtasktracker.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.example.skillboxtasktracker.entity.RoleType;

import java.util.Set;

public record UserRequest(
        @NotBlank
        String username,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password,
        @NotEmpty
        Set<RoleType> roles) {
}