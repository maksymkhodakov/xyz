package com.example.xyz.domain.entities;

import com.example.xyz.domain.enums.ItemStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Table
@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
public class Item {
    @Id
    private Long id;

    @Version
    private Long version;

    @NotBlank
    private String description;

    @NotNull
    private ItemStatus status = ItemStatus.TODO;

    private Long assigneeId;

    @Transient
    private Person assignee;

    @Transient
    private List<Tag> tags;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
