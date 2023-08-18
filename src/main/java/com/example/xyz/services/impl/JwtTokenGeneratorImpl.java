package com.example.xyz.services.impl;

import com.example.xyz.domain.entity.User;
import com.example.xyz.security.TokenDetails;
import com.example.xyz.services.JwtTokenGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtTokenGeneratorImpl implements JwtTokenGenerator {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Integer expirationInSeconds;

    @Value("${jwt.issuer}")
    private String issuer;

    @Override
    public TokenDetails generateToken(User user) {
        final Map<String, Object> claims = Map.of(
                "role", user.getRole(),
                "username", user.getUsername()
        );
        return generateToken(claims, user.getId().toString());
    }

    @Override
    public TokenDetails generateToken(Map<String, Object> claims, String subject) {
        final long expirationTimeMillis = expirationInSeconds * 1000L;
        final Date expirationDate = new Date(new Date().getTime() + expirationTimeMillis);
        return generateToken(expirationDate, claims, subject);
    }

    @Override
    public TokenDetails generateToken(Date expirationDate, Map<String, Object> claims, String subject) {
        final Date createdDate = new Date();
        final String token = Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(createdDate)
                .setId(UUID.randomUUID().toString())
                .setExpiration(expirationDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        return TokenDetails.builder()
                .token(token)
                .issuedDate(createdDate)
                .expiresDate(expirationDate)
                .build();
    }

    private Key getSigningKey() {
        byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
