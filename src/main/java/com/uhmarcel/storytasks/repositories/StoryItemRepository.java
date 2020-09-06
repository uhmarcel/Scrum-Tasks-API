package com.uhmarcel.storytasks.repositories;
import com.uhmarcel.storytasks.models.StoryItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryItemRepository extends MongoRepository<StoryItem, Long> {}
