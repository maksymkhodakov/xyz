package com.example.xyz.services;

import com.example.xyz.domain.entities.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonService {
    Flux<Person> findAll();
    Mono<Person> findById(final Long id);
}
