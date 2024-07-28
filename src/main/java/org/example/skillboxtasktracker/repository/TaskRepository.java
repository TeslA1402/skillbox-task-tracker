package org.example.skillboxtasktracker.repository;

import org.example.skillboxtasktracker.entity.Task;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TaskRepository extends ReactiveCrudRepository<Task, String> {
}