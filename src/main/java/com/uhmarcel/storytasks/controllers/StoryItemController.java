package com.uhmarcel.storytasks.controllers;
import com.uhmarcel.storytasks.models.StoryItem;
import com.uhmarcel.storytasks.models.common.Priority;
import com.uhmarcel.storytasks.models.common.Status;
import com.uhmarcel.storytasks.services.AutoIncrementService;
import com.uhmarcel.storytasks.services.StoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.uhmarcel.storytasks.configuration.ControllerConstants.*;

@RestController
@RequestMapping(APP_ROUTE_PREFIX)
public class StoryItemController {

    private Logger logger;
    private StoryService storyService;
    private AutoIncrementService autoIncrementService;


    public StoryItemController(StoryService storyService, AutoIncrementService autoIncrementService) {
        this.storyService = storyService;
        this.autoIncrementService = autoIncrementService;
        this.logger = LoggerFactory.getLogger(StoryItemController.class);
    }

    @GetMapping("/stories")
    public List<StoryItem> getAll(
        @PageableDefault(size = PAGE_SIZE_DEFAULT) Pageable page,
        @RequestParam(name = "ids", required = false) List<Long> ids,
        @RequestParam(name = "parent", required = false) Long parent,
        @RequestParam(name = "status", required = false) Status status,
        @RequestParam(name = "priority", required = false) Priority priority,
        @RequestParam(name = "search", required = false) String search,
        @RequestParam(name = "includeParent", defaultValue = "false") Boolean includeParent
    ) {
        logger.info(String.format("GetStoryItems: { parent: %s, status: %s, priority: %s, search: %s, includeParent: %s }",
                parent, status, priority, search, includeParent));
        return storyService.getAll(page, ids, parent, status, priority, search, includeParent);
    }

    @PostMapping("/stories")
    public List<StoryItem> create(@RequestBody final StoryItem story) {
        logger.info(String.format("CreateStoryItem: %s", story));
        return storyService.create(story);
    }

    @GetMapping("/stories/{id}")
    public StoryItem get(@PathVariable("id") Long id) {
        return storyService.getOne(id);
    }


    @DeleteMapping("/stories/{id}")
    public List<StoryItem> delete(@PathVariable("id") Long id) {
        return storyService.delete(id);
    }

    @PutMapping("/stories/{id}")
    public List<StoryItem> update(@PathVariable("id") Long id, @RequestBody StoryItem story) {
        logger.info(String.format("UpdateStoryItem: { id: %s, story: %s }", id, story));
        return storyService.update(id, story);
    }

}
