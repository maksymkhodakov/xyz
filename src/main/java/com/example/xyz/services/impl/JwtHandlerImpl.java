package com.example.xyz.services.impl;

import com.example.xyz.exceptions.ApiErrors;
import com.example.xyz.exceptions.ImpossibleOperationException;
import com.example.xyz.services.JwtHandler;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Date;

@RequiredArgsConstructor
public class JwtHandlerImpl implements JwtHandler {
    private final String secret;

    @Override
    public Mono<VerificationResult> check(String accessToken) {
        return Mono.just(verify(accessToken))
                .onErrorResume(e -> Mono.error(new ImpossibleOperationException(ApiErrors.UNAUTHORIZED)));
    }

    private VerificationResult verify(String token) {
        final Claims claims = getClaimsFromToken(token);
        final Date expirationDate = claims.getExpiration();
        if (expirationDate.before(new Date())) {
            throw new ImpossibleOperationException(ApiErrors.TOKEN_EXPIRED);
        }
        return new VerificationResult(claims, token);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
