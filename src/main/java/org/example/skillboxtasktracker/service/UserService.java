package org.example.skillboxtasktracker.service;

import lombok.RequiredArgsConstructor;
import org.example.skillboxtasktracker.controller.exception.AlreadyExistsException;
import org.example.skillboxtasktracker.controller.exception.NotFoundException;
import org.example.skillboxtasktracker.controller.request.UserRequest;
import org.example.skillboxtasktracker.entity.User;
import org.example.skillboxtasktracker.mapper.UserMapper;
import org.example.skillboxtasktracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new NotFoundException("User with username %s not found".formatted(username))));
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> findById(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("User with id %s not found".formatted(id))));
    }

    public Mono<Boolean> existsByUsername(String username) {
        return userRepository.findByUsername(username).map(user -> true).defaultIfEmpty(false);
    }

    public Mono<User> create(UserRequest userRequest) {
        return existsByUsername(userRequest.username()).flatMap(exists -> {
            if (Boolean.TRUE.equals(exists)) {
                return Mono.error(new AlreadyExistsException("User already exists"));
            } else {
                User user = userMapper.toEntity(userRequest, passwordEncoder.encode(userRequest.password()));
                return userRepository.save(user);
            }
        });
    }

    public Mono<User> update(String id, UserRequest userRequest) {
        return findById(id).flatMap(user -> existsByUsername(userRequest.username()).flatMap(exists -> {
            if (Boolean.TRUE.equals(exists) && !user.getUsername().equals(userRequest.username())) {
                return Mono.error(new AlreadyExistsException("User with username '%s' already exists".formatted(userRequest.username())));
            } else {
                user.setEmail(userRequest.email());
                user.setUsername(userRequest.username());
                user.setPassword(passwordEncoder.encode(userRequest.password()));
                user.setRoles(userRequest.roles());
                return userRepository.save(user);
            }
        }));
    }

    public Mono<Void> deleteById(String id) {
        return userRepository.deleteById(id);
    }

}