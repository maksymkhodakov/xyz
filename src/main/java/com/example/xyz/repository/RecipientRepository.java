package com.example.xyz.repository;

import com.example.xyz.domain.entities.Recipient;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface RecipientRepository extends R2dbcRepository<Recipient, String> {
}
