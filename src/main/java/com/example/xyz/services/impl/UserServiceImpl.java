package com.example.xyz.services.impl;

import com.example.xyz.domain.dto.AuthResponseDTO;
import com.example.xyz.domain.dto.UserDTO;
import com.example.xyz.domain.entities.User;
import com.example.xyz.mapper.AuthResponseMapper;
import com.example.xyz.mapper.UserMapper;
import com.example.xyz.repository.UserRepository;
import com.example.xyz.security.ApplicationPrincipal;
import com.example.xyz.services.SecurityService;
import com.example.xyz.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    SecurityService securityService;

    @Override
    public Mono<UserDTO> registerUser(UserDTO dto) {
        final User user = new User(dto);
        return userRepository.save(userMapper.getUser(user))
                .doOnSuccess(u -> log.info("Registered user: {}", u))
                .map(UserDTO::new);
    }

    @Override
    public Mono<AuthResponseDTO> login(String username, String password) {
        return securityService.authenticate(username, password)
                .flatMap(tokenDetails -> Mono.just(AuthResponseMapper.getAuthResponseDTO(tokenDetails)));
    }

    @Override
    public Mono<UserDTO> getUserInfo(Authentication authentication) {
        final var principal = (ApplicationPrincipal) authentication.getPrincipal();
        return userRepository.findById(principal.getId())
                .map(UserDTO::new);
    }
}
