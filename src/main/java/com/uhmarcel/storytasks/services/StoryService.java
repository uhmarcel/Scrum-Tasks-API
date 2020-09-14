package com.uhmarcel.storytasks.services;

import com.uhmarcel.storytasks.models.StoryItem;
import com.uhmarcel.storytasks.repositories.StoryItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoryService {

    private StoryItemRepository storyItemRepository;

    public StoryService(StoryItemRepository storyItemRepository) {
        this.storyItemRepository = storyItemRepository;
    };

    public StoryItem create(StoryItem story) {
        return storyItemRepository.save(story);
    }

    public List<StoryItem> get() {
        return storyItemRepository.findAll();
    }

}
