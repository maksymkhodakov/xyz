package com.example.xyz.services.impl;

import com.example.xyz.exceptions.ApiErrors;
import com.example.xyz.exceptions.ImpossibleOperationException;
import com.example.xyz.repository.UserRepository;
import com.example.xyz.security.TokenDetails;
import com.example.xyz.services.JwtTokenGenerator;
import com.example.xyz.services.SecurityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityServicesImpl implements SecurityService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JwtTokenGenerator jwtTokenGenerator;

    @Override
    public Mono<TokenDetails> authenticate(String username, String password) {
        return userRepository.findUserByUsername(username)
                .flatMap(user -> {
                    if (!user.isEnabled()) {
                        return Mono.error(new ImpossibleOperationException(ApiErrors.USER_IS_DISABLED));
                    }
                    if (!passwordEncoder.matches(password, user.getPassword())) {
                        return Mono.error(new ImpossibleOperationException(ApiErrors.PASSWORD_MISMATCH));
                    }
                    return Mono.just(jwtTokenGenerator.generateToken(user)
                            .toBuilder()
                            .userId(user.getId())
                            .build());
                })
                .switchIfEmpty(Mono.error(new ImpossibleOperationException(ApiErrors.USER_NOT_FOUND)));
    }
}
