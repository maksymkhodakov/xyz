package com.example.xyz.services;

import com.example.xyz.domain.entity.User;
import com.example.xyz.security.TokenDetails;

import java.util.Date;
import java.util.Map;

public interface JwtTokenGenerator {
    TokenDetails generateToken(User user);
    TokenDetails generateToken(final Map<String, Object> claims, final String subject);
    TokenDetails generateToken(final Date expirationDate, final Map<String, Object> claims, final String subject);
}
