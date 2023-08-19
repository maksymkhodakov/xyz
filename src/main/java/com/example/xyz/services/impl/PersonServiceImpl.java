package com.example.xyz.services.impl;

import com.example.xyz.domain.entities.Person;
import com.example.xyz.exceptions.ApiErrors;
import com.example.xyz.exceptions.ImpossibleOperationException;
import com.example.xyz.repository.PersonRepository;
import com.example.xyz.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private static final Sort DEFAULT_SORT = Sort.by(Sort.Order.by("first_name,last_name"));

    private final PersonRepository personRepository;

    @Override
    public Flux<Person> findAll() {
        return personRepository.findAll(DEFAULT_SORT);
    }

    @Override
    public Mono<Person> findById(Long id) {
        return personRepository.findById(id)
                .switchIfEmpty(Mono.error(new ImpossibleOperationException(ApiErrors.ITEM_NOT_FOUND)));
    }
}
