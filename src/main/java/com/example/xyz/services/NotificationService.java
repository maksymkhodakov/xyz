package com.example.xyz.services;

import com.example.xyz.domain.dto.NotificationDTO;
import reactor.core.publisher.Mono;

public interface NotificationService {
    Mono<NotificationDTO> findNotificationByUid(String uid);
    Mono<NotificationDTO> findNotificationWithRecipientByUid(String uid);
}
