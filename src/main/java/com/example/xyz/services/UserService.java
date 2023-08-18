package com.example.xyz.services;

import com.example.xyz.domain.dto.AuthResponseDTO;
import com.example.xyz.domain.dto.UserDTO;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserDTO> registerUser(UserDTO dto);

    Mono<AuthResponseDTO> login(String username, String password);

    Mono<UserDTO> getUserInfo(Authentication authentication);

}
