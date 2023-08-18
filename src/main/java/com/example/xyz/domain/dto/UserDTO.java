package com.example.xyz.domain.dto;

import com.example.xyz.domain.entities.UserRole;
import com.example.xyz.domain.interfaces.IUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDTO implements IUser {
    private Long id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private UserRole role;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean enabled;

    public UserDTO(IUser iUser) {
        this.id = iUser.getId();
        this.username = iUser.getUsername();
        this.password = iUser.getPassword();
        this.role = iUser.getRole();
        this.firstName = iUser.getFirstName();
        this.lastName = iUser.getLastName();
        this.createdAt = iUser.getCreatedAt();
        this.updatedAt = iUser.getUpdatedAt();
        this.enabled = iUser.isEnabled();
    }
}
