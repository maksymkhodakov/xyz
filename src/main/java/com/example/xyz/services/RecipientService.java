package com.example.xyz.services;

import com.example.xyz.domain.dto.RecipientDTO;
import reactor.core.publisher.Mono;

public interface RecipientService {
    Mono<RecipientDTO> findRecipientWithNotificationsByUid(String uid);
}
