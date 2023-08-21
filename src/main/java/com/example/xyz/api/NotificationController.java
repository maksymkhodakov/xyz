package com.example.xyz.api;

import com.example.xyz.domain.dto.NotificationDTO;
import com.example.xyz.services.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {
    NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody NotificationDTO notificationDTO) {
        notificationService.createNotification(notificationDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/basic/{uid}")
    public Mono<NotificationDTO> findNotificationById(@PathVariable String uid) {
        return notificationService.findNotificationByUid(uid);
    }

    @GetMapping("/{uid}")
    public Mono<NotificationDTO> findNotificationWithRecipientById(@PathVariable String uid) {
        return notificationService.findNotificationByUid(uid);
    }


}
