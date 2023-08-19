package com.example.xyz.mapper;

import com.example.xyz.domain.dto.NotificationDTO;
import com.example.xyz.domain.entities.Notification;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationDTO map(Notification entity);

    @InheritInverseConfiguration
    Notification map(NotificationDTO dto);
}
