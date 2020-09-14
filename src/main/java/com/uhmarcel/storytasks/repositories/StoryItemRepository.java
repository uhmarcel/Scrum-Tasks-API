package com.uhmarcel.storytasks.repositories;
import com.uhmarcel.storytasks.models.StoryItem;
import com.uhmarcel.storytasks.models.common.Priority;
import com.uhmarcel.storytasks.models.common.Status;
import com.uhmarcel.storytasks.repositories.custom.StoryItemRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryItemRepository extends MongoRepository<StoryItem, Long>, StoryItemRepositoryCustom {}
