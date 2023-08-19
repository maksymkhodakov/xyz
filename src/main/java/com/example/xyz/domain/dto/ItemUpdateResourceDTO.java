package com.example.xyz.domain.dto;

import com.example.xyz.domain.enums.ItemStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class ItemUpdateResourceDTO {
    @NotBlank
    private String description;

    @NotNull
    private ItemStatus status;

    private Long assigneeId;

    private Set<Long> tagIds;
}
