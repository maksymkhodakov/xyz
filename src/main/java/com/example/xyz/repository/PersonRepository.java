package com.example.xyz.repository;

import com.example.xyz.domain.entities.Person;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface PersonRepository extends R2dbcRepository<Person, Long> {
}
