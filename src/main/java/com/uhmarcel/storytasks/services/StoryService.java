package com.uhmarcel.storytasks.services;

import com.uhmarcel.storytasks.models.StoryItem;
import com.uhmarcel.storytasks.models.common.Identifier;
import com.uhmarcel.storytasks.models.common.Priority;
import com.uhmarcel.storytasks.models.common.Status;
import com.uhmarcel.storytasks.repositories.StoryItemRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.uhmarcel.storytasks.configuration.ControllerConstants.NO_PARENT_ID;

@Service
public class StoryService {

    private final StoryItemRepository storyItemRepository;
    private final AutoIncrementService autoIncrementService;

    public StoryService(StoryItemRepository storyItemRepository, AutoIncrementService autoIncrementService) {
        this.storyItemRepository = storyItemRepository;
        this.autoIncrementService = autoIncrementService;
    };

    public List<StoryItem> getAll(Pageable page, List<Long> ids, Long parent, Status status, Priority priority, String search, Boolean includeParent) {
        UUID userId = UserService.getUserIdentifier();
        if (ids != null) {
            List<Identifier> identifiers = ids
                .stream()
                .map(referenceId -> new Identifier(userId, referenceId))
                .collect(Collectors.toList());
            return (List<StoryItem>) storyItemRepository.findAllById(identifiers);
        }
        return storyItemRepository.findAllWithFilters(userId, parent, status, priority, search, page, includeParent);
    }


    public StoryItem getOne(Long referenceId) {
        return getOne(Identifier.from(referenceId));
    }

    public StoryItem getOne(Identifier identifier) {
        return storyItemRepository.findById(identifier).get();
    }

    public List<StoryItem> delete(Long referenceId) {
        return delete(Identifier.from(referenceId));
    }

    public List<StoryItem> delete(Identifier identifier) {
        StoryItem original = storyItemRepository.findById(identifier).get();
        List<StoryItem> updatedStories = syncParentChild(original, null);
        storyItemRepository.deleteById(identifier);
        return storyItemRepository.saveAll(updatedStories);
    }

    public List<StoryItem> create(StoryItem story) {
        story.setIdentifier(generateIdentifier());
        List<StoryItem> updatedStories = syncParentChild(null, story);
        updatedStories.add(story);
        return storyItemRepository.saveAll(updatedStories);
    }

    // TODO: Add exceptions
    public List<StoryItem> update(Long referenceId, StoryItem story) {
        StoryItem original = storyItemRepository.findById(Identifier.from(referenceId)).get();
        List<StoryItem> updatedStories = syncParentChild(original, story);

        BeanUtils.copyProperties(story, original, "id");
        updatedStories.add(original);

        return storyItemRepository.saveAll(updatedStories);
    }

    // TODO: Consider children
    private List<StoryItem> syncParentChild(StoryItem original, StoryItem next) {
        UUID userId = UserService.getUserIdentifier();
        List<StoryItem> updatedStories = new ArrayList<>();

        // If this story's parent id has changed, remove it from its old parent and add it to new
        if (original == null || next == null || original.getParent() != next.getParent()) {

            // Get original parent and remove this story off its children list
            if (original != null && original.getParent() != NO_PARENT_ID) {
                storyItemRepository
                    .findById(Identifier.from(userId, original.getParent()))
                    .ifPresent(parent -> {
                        parent.getChildren().remove(original.getIdentifier().getReferenceId());
                        updatedStories.add(parent);
                    });
            }

            // Get new parent and add this story on its children list
            if (next != null && next.getParent() != NO_PARENT_ID) {
                storyItemRepository
                    .findById(Identifier.from(userId, next.getParent()))
                    .ifPresent(parent -> {
                        if (!parent.getChildren().contains(next.getIdentifier())) {
                            parent.getChildren().add(next.getIdentifier().getReferenceId());
                            updatedStories.add(parent);
                        }
                    });
            }
        }

        return updatedStories;
    }

    private final Identifier generateIdentifier() {
        UUID userId = UserService.getUserIdentifier();
        String sequenceKey = AutoIncrementService.composeKey(StoryItem.SEQUENCE_KEY, userId.toString());
        Long referenceId = autoIncrementService.next(sequenceKey);
        return new Identifier(userId, referenceId);
    }

}
