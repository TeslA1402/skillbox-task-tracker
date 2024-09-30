package org.example.skillboxtasktracker.security;

import lombok.RequiredArgsConstructor;
import org.example.skillboxtasktracker.repository.UserRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DbUserDetailsService implements ReactiveUserDetailsService {
    private final UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username).switchIfEmpty(Mono.error(new UsernameNotFoundException(username)))
                .map(AppUserPrincipal::new);
    }
}
