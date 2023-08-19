package com.example.xyz.services.impl;

import com.example.xyz.domain.entities.Tag;
import com.example.xyz.exceptions.ApiErrors;
import com.example.xyz.exceptions.ImpossibleOperationException;
import com.example.xyz.repository.TagRepository;
import com.example.xyz.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private static final Sort DEFAULT_SORT = Sort.by(Sort.Order.by("name"));

    private final TagRepository tagRepository;

    @Override
    public Flux<Tag> findAll() {
        return tagRepository.findAll(DEFAULT_SORT);
    }

    @Override
    public Mono<Tag> findById(Long id) {
        return tagRepository.findById(id)
                .switchIfEmpty(Mono.error(new ImpossibleOperationException(ApiErrors.TAG_NOT_FOUND)));
    }
}
