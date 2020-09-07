package com.uhmarcel.storytasks.models;

import com.uhmarcel.storytasks.models.common.Priority;
import com.uhmarcel.storytasks.models.common.Size;
import com.uhmarcel.storytasks.models.common.Status;
import com.uhmarcel.storytasks.models.common.Task;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document
public class StoryItem {

    @Id private Long id;
    private String name;
    private String description;
    private List<Long> children;
    private List<Task> tasks;
    private Priority priority;
    private Size size;
    private Status status;

    public StoryItem(Long id, String name, String description, List<Long> children, List<Task> tasks, Priority priority, Size size, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.children = children;
        this.tasks = tasks;
        this.priority = priority;
        this.size = size;
        this.status = status;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getChildren() {
        return children;
    }

    public void setChildren(List<Long> children) {
        this.children = children;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
