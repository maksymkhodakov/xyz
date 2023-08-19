package com.example.xyz.domain.interfaces;

import com.example.xyz.domain.enums.UserRole;

import java.time.LocalDateTime;

public interface IUser {
    Long getId();
    String getUsername();
    String getPassword();
    UserRole getRole();
    String getFirstName();
    String getLastName();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
    boolean isEnabled();
}
