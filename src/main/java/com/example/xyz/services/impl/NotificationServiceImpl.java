package com.example.xyz.services.impl;

import com.example.xyz.domain.dto.NotificationDTO;
import com.example.xyz.mapper.NotificationMapper;
import com.example.xyz.repository.NotificationRepository;
import com.example.xyz.repository.RecipientRepository;
import com.example.xyz.services.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationServiceImpl implements NotificationService {

    NotificationRepository notificationRepository;
    RecipientRepository recipientRepository;
    NotificationMapper notificationMapper;

    @Override
    public Mono<NotificationDTO> findNotificationByUid(String uid) {
        return notificationRepository.findById(uid).map(notificationMapper::map);
    }

    @Override
    public Mono<NotificationDTO> findNotificationWithRecipientByUid(String uid) {
        return notificationRepository.findById(uid)
                .flatMap(notificationEntity -> recipientRepository.findById(notificationEntity.getRecipientUid())
                .map(recipient -> {
                    notificationEntity.setRecipient(recipient);
                    return notificationEntity;
                })
                .map(notificationMapper::map));
    }
}
