package com.example.xyz.services.impl;

import com.example.xyz.domain.entities.Item;
import com.example.xyz.domain.entities.ItemTag;
import com.example.xyz.exceptions.ApiErrors;
import com.example.xyz.exceptions.ImpossibleOperationException;
import com.example.xyz.mapper.TagMapper;
import com.example.xyz.repository.ItemRepository;
import com.example.xyz.repository.ItemTagRepository;
import com.example.xyz.repository.PersonRepository;
import com.example.xyz.repository.TagRepository;
import com.example.xyz.services.ItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemServiceImpl implements ItemService {

    private static final Sort DEFAULT_SORT = Sort.by(Sort.Order.by("lastModifiedDate"));

    ItemRepository itemRepository;
    TagRepository tagRepository;
    ItemTagRepository itemTagRepository;
    PersonRepository personRepository;
    TagMapper tagMapper;

    @Override
    public Flux<Item> findAll() {
        return itemRepository.findAll(DEFAULT_SORT)
                .flatMap(this::loadRelations);
    }

    @Override
    @Transactional
    public Mono<Item> create(Item item) {

        if (item.getId() != null || item.getVersion() != null) {
            return Mono.error(new IllegalArgumentException("When creating an item, the id and the version must be null"));
        }

        return  // Save the new item
                itemRepository.save(item)
                        // Save the links to tags
                        .flatMap(savedItem ->
                                itemTagRepository.saveAll(tagMapper.toItemTags(savedItem.getId(), savedItem.getTags()))
                                        .collectList()
                                        // Return the newly created item
                                        .then(Mono.just(savedItem)));
    }

    @Override
    public Mono<Item> update(Item itemToSave) {
        if(itemToSave.getId() == null || itemToSave.getVersion() == null) {
            return Mono.error(new IllegalArgumentException("When updating an item, the id and the version must be provided"));
        }

        return verifyExistence(itemToSave.getId())

                // Find the existing link to the tags
                .then(itemTagRepository.findAllByItemId(itemToSave.getId()).collectList())

                // Remove and add the links to the tags
                .flatMap(currentItemTags -> {

                    // As R2DBC does not support embedded IDs, the ItemTag entity has a technical key
                    // We can't just replace all ItemTags, we need to generate the proper insert/delete statements

                    final Collection<Long> existingTagIds = tagMapper.extractTagIdsFromItemTags(currentItemTags);
                    final Collection<Long> tagIdsToSave = tagMapper.extractTagIdsFromTags(itemToSave.getTags());

                    // Item Tags to be deleted
                    final Collection<ItemTag> removedItemTags = currentItemTags.stream()
                            .filter(itemTag -> !tagIdsToSave.contains(itemTag.getTagId()))
                            .collect(Collectors.toList());

                    // Item Tags to be inserted
                    final Collection<ItemTag> addedItemTags = tagIdsToSave.stream()
                            .filter(tagId -> !existingTagIds.contains(tagId))
                            .map(tagId -> new ItemTag(itemToSave.getId(), tagId))
                            .collect(Collectors.toList());

                    return itemTagRepository.deleteAll(removedItemTags)
                            .then(itemTagRepository.saveAll(addedItemTags).collectList());
                })

                // Save the item
                .then(itemRepository.save(itemToSave));
    }

    @Override
    public Mono<Void> deleteById(Long id, Long version) {
        return  // Find the item to delete
                findById(id, version, false)

                        // Delete the links to the tags
                        .zipWith(itemTagRepository.deleteAllByItemId(id))

                        // Return the item
                        .map(Tuple2::getT1)

                        // Delete the item
                        .flatMap(itemRepository::delete);
    }

    public Mono<Item> findById(final Long id, final Long version, final boolean loadRelations) {

        final Mono<Item> itemMono = itemRepository.findById(id)
                .switchIfEmpty(Mono.error(new ImpossibleOperationException(ApiErrors.ITEM_NOT_FOUND)))
                .handle((item, sink) -> {
                    // Optimistic locking: pre-check
                    if (version != null && !version.equals(item.getVersion())) {
                        // The version are different, return an error
                        sink.error(new ImpossibleOperationException(ApiErrors.UNEXPECTED_VERSION));
                    } else {
                        sink.next(item);
                    }
                });

        // Load the related objects, if requested
        return loadRelations ? itemMono.flatMap(this::loadRelations) : itemMono;
    }

    private Mono<Item> loadRelations(final Item item) {
        // Load the tags
        Mono<Item> mono = Mono.just(item)
                .zipWith(tagRepository.findTagsByItemId(item.getId()).collectList())
                .map(result -> result.getT1().setTags(result.getT2()));

        // Load the assignee (if set)
        if (item.getAssigneeId() != null) {
            mono = mono.zipWith(personRepository.findById(item.getAssigneeId()))
                    .map(result -> result.getT1().setAssignee(result.getT2()));
        }

        return mono;
    }

    private Mono<Boolean> verifyExistence(Long id) {
        return itemRepository.existsById(id).handle((exists, sink) -> {
            if (Boolean.FALSE.equals(exists)) {
                sink.error(new ImpossibleOperationException(ApiErrors.ITEM_NOT_FOUND));
            } else {
                sink.next(exists);
            }
        });
    }
}
