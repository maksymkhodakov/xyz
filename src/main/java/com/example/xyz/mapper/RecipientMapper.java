package com.example.xyz.mapper;

import com.example.xyz.domain.dto.RecipientDTO;
import com.example.xyz.domain.entities.Recipient;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipientMapper {
    RecipientDTO map(Recipient entity);

    @InheritInverseConfiguration
    Recipient map(RecipientDTO dto);
}
