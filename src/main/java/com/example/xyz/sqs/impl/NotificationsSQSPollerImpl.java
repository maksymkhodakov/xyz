package com.example.xyz.sqs.impl;

import com.example.xyz.domain.dto.NotificationDTO;
import com.example.xyz.domain.entities.Notification;
import com.example.xyz.mapper.NotificationMapper;
import com.example.xyz.repository.NotificationRepository;
import com.example.xyz.sqs.NotificationSQSPoller;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationsSQSPollerImpl implements NotificationSQSPoller {

    @Value("${sqs.notifications.queue.url}")
    @NonFinal
    String url;
    SqsAsyncClient sqsAsyncClient;
    NotificationRepository notificationRepository;
    NotificationMapper notificationMapper;

    @Override
    @SqsListener(value = "${sqs.notifications.queue.name}")
    public void receiveMessage(@Payload NotificationDTO message) {
        log.info("Received notification: {}", message);
        final Notification notification = notificationMapper.map(message);
        notificationRepository.save(notification).subscribe();
    }

    @Override
    public void sendMessage(NotificationDTO obj) {
        log.info("object send: " + obj);
        sqsAsyncClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(url)
                .messageBody(String.valueOf(obj))
                .build());
    }
}
