package com.uhmarcel.storytasks.repositories.custom;

import com.uhmarcel.storytasks.models.StoryItem;
import com.uhmarcel.storytasks.models.common.Priority;
import com.uhmarcel.storytasks.models.common.Status;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface StoryItemRepositoryCustom {

    List<StoryItem> findAllWithFilters(UUID userId, Long parent, Status status, Priority priority, Pageable page, boolean includeParent);

}
