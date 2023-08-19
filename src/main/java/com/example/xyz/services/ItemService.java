package com.example.xyz.services;

import com.example.xyz.domain.entities.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ItemService {
    Flux<Item> findAll();
    Mono<Item> create(Item item);
    Mono<Item> update(Item itemToSave);
    Mono<Void> deleteById(final Long id, final Long version);
    Mono<Item> findById(final Long id, final Long version, final boolean loadRelations);
}
