package com.uhmarcel.storytasks.models.common;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
public class Task {

    @Id private final UUID id;
    private String label;
    private boolean isDone;

    @PersistenceConstructor
    public Task(UUID id, String label, boolean isDone) {
        this.id = (id == null) ? UUID.randomUUID() : id;
        this.label = label;
        this.isDone = isDone;
    }

    public UUID getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public String toString() {
        return String.format("{ id: %s, label: %s, isDone: %s }", this.id, this.label, this.isDone);
    }
}
