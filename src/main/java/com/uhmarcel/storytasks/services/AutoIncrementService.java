package com.uhmarcel.storytasks.services;

import com.uhmarcel.storytasks.models.AutoIncrementSequence;
import com.uhmarcel.storytasks.repositories.AutoIncrementRepository;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutoIncrementService {

    private final AutoIncrementRepository autoIncrementRepository;

    public AutoIncrementService(AutoIncrementRepository autoIncrementRepository) {
        this.autoIncrementRepository = autoIncrementRepository;
    }

    public long getNext(String namespaceKey, String identifier) {
        final String sequenceKey = namespaceKey + ":" + identifier;
        return getNext(sequenceKey);
    }

    public long getNext(String sequenceKey) {
        Optional<AutoIncrementSequence> sequence = autoIncrementRepository.findById(sequenceKey);

        long next = sequence.isPresent() ? sequence.get().getSequence() + 1 : 0;
        autoIncrementRepository.save(new AutoIncrementSequence(sequenceKey, next));

        return next;
    }

}
