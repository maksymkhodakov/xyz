package com.example.xyz.api;


import com.example.xyz.domain.dto.PersonResourceDTO;
import com.example.xyz.mapper.PersonMapper;
import com.example.xyz.services.PersonService;
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
@RequestMapping(value = "api/v1/people")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PersonController {

    PersonService personService;
    PersonMapper personMapper;

    @GetMapping(value = "/{id}", produces = {APPLICATION_JSON_VALUE})
    public Mono<PersonResourceDTO> findById(@PathVariable final Long id) {
        return personService.findById(id).map(personMapper::toResource);
    }

    @GetMapping(produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<PersonResourceDTO> getAll() {
        return personService.findAll()
                .map(personMapper::toResource);
    }

}
