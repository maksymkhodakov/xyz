package com.example.xyz.api;

import com.example.xyz.domain.dto.AuthRequestDTO;
import com.example.xyz.domain.dto.AuthResponseDTO;
import com.example.xyz.domain.dto.UserDTO;
import com.example.xyz.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    UserService userService;

    @PostMapping("/register")
    public Mono<UserDTO> register(@RequestBody UserDTO dto) {
        return userService.registerUser(dto);
    }

    @PostMapping("/login")
    public Mono<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO) {
        return userService.login(authRequestDTO.getUsername(), authRequestDTO.getPassword());
    }

    @GetMapping("/info")
    public Mono<UserDTO> getUserInfo(Authentication authentication) {
        return userService.getUserInfo(authentication);
    }
}
