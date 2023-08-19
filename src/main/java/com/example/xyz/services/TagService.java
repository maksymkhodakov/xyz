package com.example.xyz.services;

import com.example.xyz.domain.entities.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TagService {
    Flux<Tag> findAll();
    Mono<Tag> findById(final Long id);
}
