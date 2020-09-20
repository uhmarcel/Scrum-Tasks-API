package com.uhmarcel.storytasks.repositories;

import com.uhmarcel.storytasks.models.StoryItem;
import com.uhmarcel.storytasks.models.User;
import com.uhmarcel.storytasks.repositories.custom.StoryItemRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<User, UUID> {}

