package com.example.xyz.sqs.impl;

import com.example.xyz.domain.dto.NotificationDTO;
import com.example.xyz.domain.entities.Notification;
import com.example.xyz.mapper.NotificationMapper;
import com.example.xyz.repository.NotificationRepository;
import com.example.xyz.sqs.NotificationSQSPoller;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationsSQSPollerImpl implements NotificationSQSPoller {

    @NonFinal
    @Value("${sqs.notifications.queue.name}")
    String name;

    QueueMessagingTemplate queueMessagingTemplate;
    NotificationRepository notificationRepository;
    NotificationMapper notificationMapper;

    @Override
    @SqsListener(value = "${sqs.notifications.queue.name}")
    public void receiveMessage(@Payload NotificationDTO message) {
        log.info("Received notification with id: {}.", message.id());
        final Notification notification = notificationMapper.map(message);
        notificationRepository.save(notification).subscribe();
    }

    @Override
    public void sendMessage(final NotificationDTO message) {
        Message<String> msg = MessageBuilder.withPayload(message.toString())
                .setHeader("message-group-id", UUID.randomUUID().toString()) // we do not need ordering in messages
                .setHeader("message-deduplication-id", UUID.randomUUID().toString()) // we process all messaged in queue
                .build();
        queueMessagingTemplate.convertAndSend(name, msg);
        log.info("Notification with id: {} was sent.", message.id());
    }
}
