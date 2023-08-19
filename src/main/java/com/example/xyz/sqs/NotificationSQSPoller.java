package com.example.xyz.sqs;

import com.example.xyz.domain.dto.NotificationDTO;

public interface NotificationSQSPoller {
    void receiveMessage(NotificationDTO message);
    void sendMessage(NotificationDTO obj);
}
