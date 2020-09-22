package com.uhmarcel.storytasks.repositories;

import com.uhmarcel.storytasks.models.AutoIncrementSequence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoIncrementRepository extends MongoRepository<AutoIncrementSequence, String> {}
