package com.example.xyz.api;

import com.example.xyz.domain.dto.ItemPatchResourceDTO;
import com.example.xyz.domain.dto.ItemResourceDTO;
import com.example.xyz.domain.dto.ItemUpdateResourceDTO;
import com.example.xyz.domain.dto.NewItemResourceDTO;
import com.example.xyz.mapper.ItemMapper;
import com.example.xyz.services.ItemService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/items")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemController {

    ItemService itemService;
    ItemMapper itemMapper;

    @PostMapping
    public Mono<ResponseEntity<ItemResourceDTO>> create(@Valid @RequestBody final NewItemResourceDTO newItemResource) {
        return Mono.just(ResponseEntity.ok(itemMapper.toResource(itemService.create(itemMapper.toModel(newItemResource)).block())));
    }

    @PutMapping(value = "/{id}")
    public Mono<ResponseEntity<Void>> update(@PathVariable final Long id,
                                             @RequestHeader final Long version,
                                             @Valid @RequestBody final ItemUpdateResourceDTO itemUpdateResource) {

        return itemService.findById(id, version, false)
                .map(item -> itemMapper.update(itemUpdateResource, item))
                .flatMap(itemService::update)
                .map(item -> ResponseEntity.noContent().build());
    }

    @PatchMapping(value = "/{id}")
    public Mono<ResponseEntity<Void>> patch(@PathVariable final Long id,
                                            @RequestHeader(value = HttpHeaders.IF_MATCH) final Long version,
                                            @Valid @RequestBody final ItemPatchResourceDTO patch) {

        return itemService.findById(id, version, true)
                .map(item -> itemMapper.patch(patch, item))
                .flatMap(itemService::update)
                .map(itemId -> ResponseEntity.noContent().build());
    }

    @GetMapping(value = "/{id}", produces = {APPLICATION_JSON_VALUE})
    public Mono<ItemResourceDTO> findById(@PathVariable final Long id) {

        return itemService.findById(id, null, true).map(itemMapper::toResource);
    }

    @GetMapping(produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<ItemResourceDTO> getAllItems() {
        return itemService.findAll()
                .map(itemMapper::toResource);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable final Long id,
                                             @RequestHeader(value = HttpHeaders.IF_MATCH) final Long version) {

        return itemService.deleteById(id, version)
                .then(Mono.fromCallable(() -> ResponseEntity.noContent().build()));
    }
}
