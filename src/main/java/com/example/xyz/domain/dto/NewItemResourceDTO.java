package com.example.xyz.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class NewItemResourceDTO {
    @NotBlank
    private String description;

    private Long assigneeId;

    private Set<Long> tagIds;
}
