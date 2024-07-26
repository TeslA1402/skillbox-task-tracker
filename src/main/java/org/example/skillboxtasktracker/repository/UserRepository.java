package org.example.skillboxtasktracker.repository;

import org.example.skillboxtasktracker.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, String> {
}