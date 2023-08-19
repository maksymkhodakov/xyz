package com.example.xyz.mapper;

import com.example.xyz.domain.dto.PersonResourceDTO;
import com.example.xyz.domain.entities.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonResourceDTO toResource(Person person);

}
