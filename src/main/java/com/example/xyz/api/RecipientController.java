package com.example.xyz.api;

import com.example.xyz.domain.dto.RecipientDTO;
import com.example.xyz.services.RecipientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recipients")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RecipientController {
    RecipientService recipientService;

    @GetMapping("/{uid}")
    public Mono<RecipientDTO> findRecipientByUid(@PathVariable String uid) {
        return recipientService.findRecipientWithNotificationsByUid(uid);
    }

}
