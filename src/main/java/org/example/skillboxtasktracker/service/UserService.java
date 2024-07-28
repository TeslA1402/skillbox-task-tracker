package org.example.skillboxtasktracker.service;

import lombok.RequiredArgsConstructor;
import org.example.skillboxtasktracker.controller.exception.NotFoundException;
import org.example.skillboxtasktracker.controller.request.UserRequest;
import org.example.skillboxtasktracker.entity.User;
import org.example.skillboxtasktracker.mapper.UserMapper;
import org.example.skillboxtasktracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> findById(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("User with id %s not found".formatted(id))));
    }

    public Mono<User> create(UserRequest userRequest) {
        User user = userMapper.toEntity(userRequest);
        return userRepository.save(user);
    }

    public Mono<User> update(String id, UserRequest userRequest) {
        return findById(id).flatMap(user -> {
            user.setEmail(userRequest.email());
            user.setUsername(userRequest.username());
            return userRepository.save(user);
        });
    }

    public Mono<Void> deleteById(String id) {
        return userRepository.deleteById(id);
    }

}