package com.example.xyz.services.impl;

import com.example.xyz.domain.entity.User;
import com.example.xyz.exceptions.ApiErrors;
import com.example.xyz.exceptions.ImpossibleOperationException;
import com.example.xyz.repository.UserRepository;
import com.example.xyz.security.ApplicationPrincipal;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationManager implements ReactiveAuthenticationManager {

    UserRepository userRepository;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        final ApplicationPrincipal principal = (ApplicationPrincipal) authentication.getPrincipal();
        return userRepository.findById(principal.getId())
                .filter(User::isEnabled)
                .switchIfEmpty(Mono.error(new ImpossibleOperationException(ApiErrors.UNAUTHORIZED)))
                .map(user -> authentication);
    }
}
