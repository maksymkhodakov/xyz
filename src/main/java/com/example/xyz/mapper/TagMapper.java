package com.example.xyz.mapper;

import com.example.xyz.domain.dto.TagResourceDTO;
import com.example.xyz.domain.entities.ItemTag;
import com.example.xyz.domain.entities.Tag;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagResourceDTO toResource(Tag person);

    default List<Tag> toTags(Collection<Long> tagsId) {

        if(tagsId == null) {
            return new ArrayList<>();
        }

        // Build tags containing only the ID
        return tagsId.stream()
                .map(tagId -> new Tag().setId(tagId))
                .toList();
    }

    default Collection<Long> extractTagIdsFromTags(Collection<Tag> tags) {
        if (tags == null) {
            return new LinkedHashSet<>();
        }

        return tags.stream().map(Tag::getId).collect(Collectors.toSet());
    }

    default Collection<Long> extractTagIdsFromItemTags(Collection<ItemTag> itemTags) {
        if (itemTags == null) {
            return new LinkedHashSet<>();
        }

        return itemTags.stream().map(ItemTag::getTagId).collect(Collectors.toSet());
    }


    default Collection<ItemTag> toItemTags(Long itemId, Collection<Tag> tags) {
        if (tags == null) {
            return new LinkedHashSet<>();
        }
        return tags.stream()
                .map(tag -> new ItemTag(itemId, tag.getId()))
                .collect(Collectors.toSet());
    }
}
