package com.example.xyz.repository;

import com.example.xyz.domain.entity.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<User, Long> {
    Mono<User> findUserByUsername(String username);
}
