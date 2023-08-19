package com.example.xyz.api;

import com.example.xyz.domain.dto.TagResourceDTO;
import com.example.xyz.mapper.TagMapper;
import com.example.xyz.services.TagService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/tags")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagController {

    TagService tagService;
    TagMapper tagMapper;

    @GetMapping(value = "/{id}", produces = {APPLICATION_JSON_VALUE})
    public Mono<TagResourceDTO> findById(@PathVariable final Long id) {
        return tagService.findById(id).map(tagMapper::toResource);
    }

    @GetMapping(produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<TagResourceDTO> getAll() {
        return tagService.findAll()
                .map(tagMapper::toResource);
    }

}
