package com.uhmarcel.storytasks.controllers;
import com.uhmarcel.storytasks.models.StoryItem;
import com.uhmarcel.storytasks.models.common.Identifier;
import com.uhmarcel.storytasks.models.common.Priority;
import com.uhmarcel.storytasks.models.common.Status;
import com.uhmarcel.storytasks.repositories.StoryItemRepository;
import com.uhmarcel.storytasks.services.AutoIncrementService;
import com.uhmarcel.storytasks.services.StoryService;
import com.uhmarcel.storytasks.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.uhmarcel.storytasks.configuration.ControllerConstants.*;

@RestController
@RequestMapping(APP_ROUTE_PREFIX)
public class StoryItemController {

    private Logger log;
    private StoryService storyService;
    private AutoIncrementService autoIncrementService;


    public StoryItemController(StoryService storyService, AutoIncrementService autoIncrementService) {
        this.storyService = storyService;
        this.autoIncrementService = autoIncrementService;
        this.log = LoggerFactory.getLogger(StoryItemController.class);
    }

    @GetMapping("/stories")
    public List<StoryItem> getAll(
        @PageableDefault(size = PAGE_SIZE_DEFAULT) Pageable page,
        @RequestParam(name = "ids", required = false) List<Long> ids,
        @RequestParam(name = "parent", required = false) Long parent,
        @RequestParam(name = "status", required = false) Status status,
        @RequestParam(name = "priority", required = false) Priority priority,
        @RequestParam(name = "includeParent", defaultValue = "false") Boolean includeParent
    ) {
        log.info(String.format("GetStoryItems: { parent: %s, status: %s, priority: %s, includeParent: %s }", parent, status, priority, includeParent));
        return storyService.getAll(page, ids, parent, status, priority, includeParent);
    }

    @PostMapping("/stories")
    public List<StoryItem> create(@RequestBody final StoryItem story) {
        log.info(String.format("CreateStoryItem: %s", story));

        // FIXME: Will increase increment counter even if creating the story fails -> Change to trigger
        UUID userID = UserService.getUserIdentifier();
        Long storyID = autoIncrementService.getNext(StoryItem.SEQUENCE_KEY, userID.toString());
        story.setIdentifier(new Identifier(userID, storyID));

        return storyService.create(story);
    }

    @GetMapping("/stories/{id}")
    public StoryItem get(@PathVariable("id") Long id) {
        return storyService.getOne(id);
    }


    @DeleteMapping("/stories/{id}")
    public void delete(@PathVariable("id") Long id) {
        storyService.delete(id);
    }

    @PutMapping("/stories/{id}")
    public List<StoryItem> update(@PathVariable("id") Long id, @RequestBody StoryItem story) {
        log.info(String.format("UpdateStoryItem: { id: %s, story: %s }", id, story));
        return storyService.update(id, story);
    }

//    @PostMapping("/generate")
//    public List<StoryItem> generate() {
//        List<StoryItem> tasks = new ArrayList<>();
//        tasks.add(new StoryItem(1L, "Story 1", "Description 1", Arrays.asList(), Arrays.asList(new Task("Do work")), Priority.CRITICAL, Size.M, Status.TODO));
//        tasks.add(new StoryItem(2L, "Story 2", "Description 2", Arrays.asList(1L), Arrays.asList(), Priority.CRITICAL, Size.M, Status.TODO));
//        tasks.add(new StoryItem(3L, "Story 3", "Description 3", Arrays.asList(1L, 4L), Arrays.asList(), Priority.MEDIUM, Size.M, Status.TODO));
//        tasks.add(new StoryItem(4L, "Story 4", "Description 4", Arrays.asList(), Arrays.asList(), Priority.CRITICAL, Size.L, Status.TODO));
//        storyItemRepository.saveAll(tasks);
//        return tasks;
//    }

//    @GetMapping("/test")
//    public Authentication get() {
//        return SecurityContextHolder.getContext().getAuthentication();
//    }

}
