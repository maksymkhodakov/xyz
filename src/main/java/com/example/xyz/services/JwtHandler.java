package com.example.xyz.services;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

public interface JwtHandler {

    Mono<VerificationResult> check(String accessToken);

    class VerificationResult {
       public Claims claims;
       public String token;

       public VerificationResult(Claims claims, String token) {
           this.claims = claims;
           this.token = token;
       }
    }
}
