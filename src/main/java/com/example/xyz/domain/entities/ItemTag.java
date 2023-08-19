package com.example.xyz.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class ItemTag {
    @Id
    private Long id;

    private Long itemId;

    private Long tagId;

    public ItemTag(Long itemId, Long tagId) {
        this.itemId = itemId;
        this.tagId = tagId;
    }
}
