package com.example.xyz.services;

import com.example.xyz.security.ApplicationPrincipal;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserAuthenticationBearer {
    static Mono<Authentication> create(JwtHandler.VerificationResult verificationResult) {
        final Claims claims = verificationResult.claims;
        final String subject = claims.getSubject();
        final String role = claims.get("role", String.class);
        final String username = claims.get("username", String.class);

        final List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        final Long principalId = Long.parseLong(subject);
        final ApplicationPrincipal principal = new ApplicationPrincipal(principalId, username);

        return Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(principal, null, authorities));
    }
}
