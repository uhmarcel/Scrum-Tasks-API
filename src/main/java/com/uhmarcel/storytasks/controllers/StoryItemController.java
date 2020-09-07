package com.uhmarcel.storytasks.controllers;
import com.uhmarcel.storytasks.models.StoryItem;
import com.uhmarcel.storytasks.models.common.Priority;
import com.uhmarcel.storytasks.models.common.Size;
import com.uhmarcel.storytasks.models.common.Status;
import com.uhmarcel.storytasks.models.common.Task;
import com.uhmarcel.storytasks.repositories.StoryItemRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/story")
public class StoryItemController {

    @Autowired
    private StoryItemRepository storyItemRepository;

    @GetMapping
    public List<StoryItem> getAll() {
        return storyItemRepository.findAll();
    }

    @GetMapping("/{id}")
    public StoryItem get(@PathVariable Long id) {
        return storyItemRepository.findById(id).get();
    }

    @PostMapping
    public StoryItem create(@RequestBody final StoryItem item) {
        return storyItemRepository.save(item);
    } // TODO: add validation

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        storyItemRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public StoryItem update(@PathVariable Long id, @RequestBody StoryItem story) {
        StoryItem previous = storyItemRepository.findById(id).get();
        BeanUtils.copyProperties(story, previous, "id");
        return storyItemRepository.save(story);
    }

    @PostMapping("/generate")
    public List<StoryItem> generate() {
        List<StoryItem> tasks = new ArrayList<>();
        tasks.add(new StoryItem(1L, "Story 1", "Description 1", Arrays.asList(), Arrays.asList(new Task("Do work")), Priority.CRITICAL, Size.M, Status.TODO));
        tasks.add(new StoryItem(2L, "Story 2", "Description 2", Arrays.asList(1L), Arrays.asList(), Priority.CRITICAL, Size.M, Status.TODO));
        tasks.add(new StoryItem(3L, "Story 3", "Description 3", Arrays.asList(1L, 4L), Arrays.asList(), Priority.MEDIUM, Size.M, Status.TODO));
        tasks.add(new StoryItem(4L, "Story 4", "Description 4", Arrays.asList(), Arrays.asList(), Priority.CRITICAL, Size.L, Status.TODO));
        storyItemRepository.saveAll(tasks);
        return tasks;
    }

}
