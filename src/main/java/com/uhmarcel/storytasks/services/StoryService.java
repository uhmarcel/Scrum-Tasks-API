package com.uhmarcel.storytasks.services;

import com.uhmarcel.storytasks.models.StoryItem;
import com.uhmarcel.storytasks.models.common.Identifier;
import com.uhmarcel.storytasks.models.common.Priority;
import com.uhmarcel.storytasks.models.common.Status;
import com.uhmarcel.storytasks.repositories.StoryItemRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.uhmarcel.storytasks.configuration.ControllerConstants.NO_PARENT_ID;

@Service
public class StoryService {

    private final StoryItemRepository storyItemRepository;

    public StoryService(StoryItemRepository storyItemRepository) {
        this.storyItemRepository = storyItemRepository;
    };

    public List<StoryItem> getAll(Pageable page, List<Long> ids, Long parent, Status status, Priority priority, Boolean includeParent) {
        UUID userId = UserService.getUserIdentifier();
        if (ids != null) {
            List<Identifier> identifiers = ids
                    .stream()
                    .map(referenceId -> new Identifier(userId, referenceId))
                    .collect(Collectors.toList());
            return (List<StoryItem>) storyItemRepository.findAllById(identifiers);
        }
        return storyItemRepository.findAllWithFilters(userId, parent, status, priority, page, includeParent);
    }


    public StoryItem getOne(Long referenceId) {
        return getOne(Identifier.from(referenceId));
    }

    public StoryItem getOne(Identifier identifier) {
        return storyItemRepository.findById(identifier).get();
    }

    public void delete(Long referenceId) {
        delete(Identifier.from(referenceId));
    }

    public void delete(Identifier identifier) {
        storyItemRepository.deleteById(identifier);
    }

    public List<StoryItem> create(StoryItem story) {
        List<StoryItem> updatedStories = new ArrayList<>();

        // TODO: Add validation
        if (story.getParent() != null && story.getParent() != NO_PARENT_ID) {
            StoryItem parent = storyItemRepository.findById(Identifier.from(story.getParent())).get(); // TODO: Add exceptions, and HTTP codes

            List<Long> children = parent.getChildren();
            if (!children.contains(story.getIdentifier())) {
                children.add(story.getIdentifier().getReferenceId());
                updatedStories.add(parent);
            }
        }
        updatedStories.add(story);

        return storyItemRepository.saveAll(updatedStories);
    }

    public List<StoryItem> update(Long referenceId, StoryItem story) {
        UUID userId = UserService.getUserIdentifier();
        StoryItem original = storyItemRepository.findById(Identifier.from(userId, referenceId)).get();
        List<StoryItem> updatedStories = new ArrayList<>();

        if (original.getParent() != story.getParent()) {

            if (original.getParent() != -1) {
                StoryItem parent = storyItemRepository.findById(Identifier.from(userId, original.getParent())).get();
                parent.getChildren().remove(original.getIdentifier());
                updatedStories.add(parent);
            }

            if (story.getParent() != -1) {
                StoryItem parent = storyItemRepository.findById(Identifier.from(userId, story.getParent())).get();

                if (!parent.getChildren().contains(story.getIdentifier())) {
                    parent.getChildren().remove(story.getIdentifier());
                    updatedStories.add(parent);
                }
            }
        }

        BeanUtils.copyProperties(story, original, "id");
        updatedStories.add(original);

        return storyItemRepository.saveAll(updatedStories);
    }

}
