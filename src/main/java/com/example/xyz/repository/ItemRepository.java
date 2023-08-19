package com.example.xyz.repository;

import com.example.xyz.domain.entities.Item;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ItemRepository extends R2dbcRepository<Item, Long> {
}
