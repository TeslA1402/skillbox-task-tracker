package org.example.skillboxtasktracker.service;

import lombok.RequiredArgsConstructor;
import org.example.skillboxtasktracker.controller.exception.NotFoundException;
import org.example.skillboxtasktracker.controller.request.TaskRequest;
import org.example.skillboxtasktracker.entity.Task;
import org.example.skillboxtasktracker.entity.User;
import org.example.skillboxtasktracker.mapper.TaskMapper;
import org.example.skillboxtasktracker.repository.TaskRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;

    public Flux<Task> findAll() {
        return taskRepository.findAll()
                .flatMap(this::populateTask);
    }

    public Mono<Task> findById(String id) {
        return findByIdWithoutPopulate(id)
                .flatMap(this::populateTask);
    }

    private Mono<Task> findByIdWithoutPopulate(String id) {
        return taskRepository.findById(id).switchIfEmpty(Mono.error(new NotFoundException("Task with id %s not found".formatted(id))));
    }

    public Mono<Task> create(TaskRequest taskRequest) {
        Instant now = Instant.now();
        Task task = taskMapper.toEntity(taskRequest, now, now);
        return populateTask(task).flatMap(taskRepository::save);
    }

    public Mono<Task> update(String id, TaskRequest taskRequest) {
        Task task = taskMapper.toEntity(taskRequest, null, Instant.now());
        return findByIdWithoutPopulate(id)
                .map(taskForUpdate -> {
                    taskForUpdate.setName(task.getName());
                    taskForUpdate.setDescription(task.getDescription());
                    taskForUpdate.setStatus(task.getStatus());
                    taskForUpdate.setAuthorId(task.getAuthorId());
                    taskForUpdate.setAssigneeId(task.getAssigneeId());
                    taskForUpdate.setObserverIds(task.getObserverIds());
                    taskForUpdate.setUpdatedAt(task.getUpdatedAt());
                    return taskForUpdate;
                }).flatMap(this::populateTask).flatMap(taskRepository::save);
    }

    public Mono<Void> deleteById(String id) {
        return taskRepository.deleteById(id);
    }

    public Mono<Task> addObserver(String taskId, String observerId) {
        return findById(taskId)
                .map(task -> {
                    task.getObserverIds().add(observerId);
                    return task;
                })
                .flatMap(this::populateTask).flatMap(taskRepository::save);
    }

    private Mono<Task> populateTask(Task task) {
        Mono<User> authorMono = userService.findById(task.getAuthorId());
        Mono<User> assigneeMono = userService.findById(task.getAssigneeId());
        Flux<User> observersFlux = Flux.fromIterable(task.getObserverIds()).flatMap(userService::findById);

        return Mono.zip(authorMono, assigneeMono, observersFlux.collectList())
                .map(tuple -> {
                    task.setAuthor(tuple.getT1());
                    task.setAssignee(tuple.getT2());
                    task.setObservers(Set.copyOf(tuple.getT3()));
                    return task;
                });
    }
}