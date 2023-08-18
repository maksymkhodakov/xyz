package com.example.xyz.mapper;

import com.example.xyz.domain.dto.AuthResponseDTO;
import com.example.xyz.security.TokenDetails;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthResponseMapper {
    public AuthResponseDTO getAuthResponseDTO(TokenDetails tokenDetails) {
        return AuthResponseDTO.builder()
                .userId(tokenDetails.getUserId())
                .token(tokenDetails.getToken())
                .issuedAt(tokenDetails.getIssuedDate())
                .expiresAt(tokenDetails.getExpiresDate())
                .build();
    }
}
