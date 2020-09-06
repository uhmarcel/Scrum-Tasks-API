package com.uhmarcel.storytasks.models.common;

import org.springframework.data.annotation.PersistenceConstructor;

public class Task {

    private String label;
    private boolean isDone;

    public Task(String label) {
        this(label, false);
    }

    @PersistenceConstructor
    public Task(String label, boolean isDone) {
        this.label = label;
        this.isDone = isDone;
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
}
