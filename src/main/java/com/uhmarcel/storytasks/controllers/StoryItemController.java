package com.uhmarcel.storytasks.controllers;
import com.uhmarcel.storytasks.models.StoryItem;
import com.uhmarcel.storytasks.models.common.Priority;
import com.uhmarcel.storytasks.models.common.Status;
import com.uhmarcel.storytasks.models.common.Task;
import com.uhmarcel.storytasks.repositories.StoryItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.uhmarcel.storytasks.configuration.ApplicationConstants.*;

@RestController
@RequestMapping(APP_ROUTE_PREFIX + STORY_ROUTE)
public class StoryItemController {

    @Autowired private StoryItemRepository storyItemRepository;
    private Logger log = LoggerFactory.getLogger(StoryItemController.class);

    @GetMapping
    public List<StoryItem> getAll(
        @PageableDefault(size = PAGE_SIZE_DEFAULT) Pageable page,
        @RequestParam(name="ids", required = false) List<Long> ids,
        @RequestParam(name = "parent", required = false) Long parent,
        @RequestParam(name = "status", required = false) Status status,
        @RequestParam(name = "priority", required = false) Priority priority,
        @RequestParam(name = "includeParent", defaultValue = "false") Boolean includeParent
    ) {
        log.info(String.format(
            "GetStoryItems: { parent: %s, status: %s, priority: %s, includeParent: %s }",
            parent, status, priority, includeParent
        ));

        if (ids != null) return (List<StoryItem>) storyItemRepository.findAllById(ids);
        return storyItemRepository.findAllWithFilters(parent, status, priority, page, includeParent);
    }

    @GetMapping("/{id}")
    public StoryItem get(@PathVariable Long id) {
        return storyItemRepository.findById(id).get();
    }

    @PostMapping
    public List<StoryItem> create(@RequestBody final StoryItem story) {
        log.info(String.format("CreateStoryItem: %s", story));

        List<StoryItem> updatedStories = new ArrayList<>();

        // TODO: Add validation
        if (story.getParent() != null && story.getParent() != NO_PARENT_ID) {
            StoryItem parent = storyItemRepository.findById(story.getParent()).get(); // TODO: Add exceptions, and HTTP codes

            List<Long> children = parent.getChildren();
            if (!children.contains(story.getId())) {
                children.add(story.getId());
                updatedStories.add(parent);
            }
        }
        updatedStories.add(story);

        return storyItemRepository.saveAll(updatedStories);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        storyItemRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public List<StoryItem> update(@PathVariable Long id, @RequestBody StoryItem story) {
        log.info(String.format("UpdateStoryItem: { id: %s, story: %s }", id, story));
        StoryItem original = storyItemRepository.findById(id).get();
        List<StoryItem> updatedStories = new ArrayList<>();

        if (original.getParent() != story.getParent()) {

            if (original.getParent() != -1) {
                StoryItem parent = storyItemRepository.findById(original.getParent()).get();
                parent.getChildren().remove(original.getId());
                updatedStories.add(parent);
            }

            if (story.getParent() != -1) {
                StoryItem parent = storyItemRepository.findById(story.getParent()).get();

                if (!parent.getChildren().contains(story.getId())) {
                    parent.getChildren().remove(story.getId());
                    updatedStories.add(parent);
                }
            }
        }

        BeanUtils.copyProperties(story, original, "id");
        updatedStories.add(original);

        return storyItemRepository.saveAll(updatedStories);
    }

    @PostMapping("/generate")
    public List<StoryItem> generate() {
        List<StoryItem> tasks = new ArrayList<>();
//        tasks.add(new StoryItem(1L, "Story 1", "Description 1", Arrays.asList(), Arrays.asList(new Task("Do work")), Priority.CRITICAL, Size.M, Status.TODO));
//        tasks.add(new StoryItem(2L, "Story 2", "Description 2", Arrays.asList(1L), Arrays.asList(), Priority.CRITICAL, Size.M, Status.TODO));
//        tasks.add(new StoryItem(3L, "Story 3", "Description 3", Arrays.asList(1L, 4L), Arrays.asList(), Priority.MEDIUM, Size.M, Status.TODO));
//        tasks.add(new StoryItem(4L, "Story 4", "Description 4", Arrays.asList(), Arrays.asList(), Priority.CRITICAL, Size.L, Status.TODO));
        storyItemRepository.saveAll(tasks);
        return tasks;
    }

}
