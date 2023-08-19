package com.example.xyz.domain.dto;

import com.example.xyz.domain.entities.Tag;
import com.example.xyz.domain.enums.ItemStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class ItemResourceDTO {
    private Long id;
    private Long version;

    private String description;
    private ItemStatus status;

    private PersonResourceDTO assignee;
    private List<Tag> tags;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
