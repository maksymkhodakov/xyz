package com.example.xyz.api;

import com.example.xyz.domain.dto.NotificationDTO;
import com.example.xyz.services.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {
    NotificationService notificationService;

    @GetMapping("/basic/{uid}")
    public Mono<NotificationDTO> findNotificationById(@PathVariable String uid) {
        return notificationService.findNotificationByUid(uid);
    }

    @GetMapping("/{uid}")
    public Mono<NotificationDTO> findNotificationWithRecipientById(@PathVariable String uid) {
        return notificationService.findNotificationByUid(uid);
    }


}
