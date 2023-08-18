package com.example.xyz.domain.entity;

import com.example.xyz.Utils.OWASPUtil;
import com.example.xyz.domain.entities.UserRole;
import com.example.xyz.domain.interfaces.IUser;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class User implements IUser {
    @Id
    private Long id;
    private String username;
    private String password;
    private UserRole role;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ToString.Include(name = "password")
    private String maskPassword() {
        return OWASPUtil.maskValue(password);
    }
}
