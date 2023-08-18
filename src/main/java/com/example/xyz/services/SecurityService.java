package com.example.xyz.services;

import com.example.xyz.security.TokenDetails;
import reactor.core.publisher.Mono;

public interface SecurityService {
    Mono<TokenDetails> authenticate(String username, String password);
}
