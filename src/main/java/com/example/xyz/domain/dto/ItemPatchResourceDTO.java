package com.example.xyz.domain.dto;

import com.example.xyz.domain.enums.ItemStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Optional;
import java.util.Set;

@Data
@Accessors(chain = true)
public class ItemPatchResourceDTO {
    private Optional<@NotBlank String> description;
    private Optional<@NotNull ItemStatus> status;
    private Optional<Long> assigneeId;
    private Optional<Set<Long>> tagIds;
}
