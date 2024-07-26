package org.example.skillboxtasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.example.skillboxtasktracker.controller.request.TaskRequest;
import org.example.skillboxtasktracker.controller.response.TaskResponse;
import org.example.skillboxtasktracker.mapper.TaskMapper;
import org.example.skillboxtasktracker.service.TaskService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    public Flux<TaskResponse> findAll() {
        return taskService.findAll().map(taskMapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<TaskResponse> findById(@PathVariable String id) {
        return taskService.findById(id).map(taskMapper::toResponse);
    }

    @PostMapping
    public Mono<TaskResponse> create(@RequestBody TaskRequest taskRequest) {
        return taskService.create(taskRequest).map(taskMapper::toResponse);
    }

    @PutMapping("/{id}")
    public Mono<TaskResponse> update(@PathVariable String id, @RequestBody TaskRequest taskRequestDTO) {
        return taskService.update(id, taskRequestDTO).map(taskMapper::toResponse);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable String id) {
        return taskService.deleteById(id);
    }

    @PostMapping("/{id}/observers/{observerId}")
    public Mono<TaskResponse> addObserver(@PathVariable String id, @PathVariable String observerId) {
        return taskService.addObserver(id, observerId).map(taskMapper::toResponse);
    }
}