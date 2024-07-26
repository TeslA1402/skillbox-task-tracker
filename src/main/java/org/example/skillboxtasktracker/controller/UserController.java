package org.example.skillboxtasktracker.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.skillboxtasktracker.controller.request.UserRequest;
import org.example.skillboxtasktracker.controller.response.UserResponse;
import org.example.skillboxtasktracker.mapper.UserMapper;
import org.example.skillboxtasktracker.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public Flux<UserResponse> findAll() {
        return userService.findAll().map(userMapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<UserResponse> findById(@PathVariable String id) {
        return userService.findById(id).map(userMapper::toResponse);
    }

    @PostMapping
    public Mono<UserResponse> create(@Valid @RequestBody UserRequest userRequest) {
        return userService.create(userRequest).map(userMapper::toResponse);
    }

    @PutMapping("/{id}")
    public Mono<UserResponse> update(@PathVariable String id, @Valid @RequestBody UserRequest userRequest) {
        return userService.update(id, userRequest).map(userMapper::toResponse);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable String id) {
        return userService.deleteById(id);
    }
}