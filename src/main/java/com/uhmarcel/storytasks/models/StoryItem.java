package com.uhmarcel.storytasks.models;

import com.uhmarcel.storytasks.models.common.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class StoryItem {
    @Transient
    public static final String SEQUENCE_KEY = "STORY_ITEM";

    @Id private Identifier identifier;
    private Long parent;
    private List<Long> children;
    private String title;
    private String description;
    private List<Task> tasks;
    private Priority priority;
    private Size size;
    private Status status;

    public StoryItem(Identifier identifier, Long parent, List<Long> children, String title, String description, List<Task> tasks, Priority priority, Size size, Status status) {
        this.identifier = identifier != null ? identifier : Identifier.from((long) Math.floor(Math.random() * 10000));
        this.parent = parent != null ? parent : -1;
        this.children = children != null ? children : new ArrayList<>();
        this.title = title;
        this.description = description;
        this.tasks = tasks != null ? tasks : new ArrayList<>();
        this.priority = priority;
        this.size = size;
        this.status = status;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public List<Long> getChildren() {
        return children;
    }

    public void setChildren(List<Long> children) {
        this.children = children;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String toString() {
        return String.format(
            "{ identifier: %s, parent: %s, children: %s, title: %s, description: %s, tasks: %s, priority: %s, size: %s, status: %s }",
            this.identifier,
            this.parent,
            this.children,
            this.title,
            this.description,
            this.tasks,
            this.priority,
            this.size,
            this.status
        );
    }
}
