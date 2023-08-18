package com.example.xyz.mapper;

import com.example.xyz.domain.dto.UserDTO;
import com.example.xyz.domain.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO map(User user);

    @InheritInverseConfiguration
    User map(UserDTO userDto);
}
