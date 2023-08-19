package com.example.xyz.services.impl;

import com.example.xyz.domain.dto.RecipientDTO;
import com.example.xyz.domain.entities.Notification;
import com.example.xyz.domain.entities.Recipient;
import com.example.xyz.mapper.RecipientMapper;
import com.example.xyz.repository.NotificationRepository;
import com.example.xyz.repository.RecipientRepository;
import com.example.xyz.services.RecipientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RecipientServiceImpl implements RecipientService {
    NotificationRepository notificationRepository;
    RecipientRepository recipientRepository;
    RecipientMapper recipientMapper;

    @Override
    public Mono<RecipientDTO> findRecipientWithNotificationsByUid(String uid) {
        return Mono.zip(recipientRepository.findById(uid),
                notificationRepository.findAllByRecipientUid(uid).collectList())
                .map(tuples -> {
                    Recipient recipient = tuples.getT1();
                    List<Notification> notifications = tuples.getT2();
                    recipient.setNotifications(notifications);
                    return recipientMapper.map(recipient);
                });
    }
}
