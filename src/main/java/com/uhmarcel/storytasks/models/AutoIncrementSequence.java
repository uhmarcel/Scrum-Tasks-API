package com.uhmarcel.storytasks.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AutoIncrementSequence {

    @Id private String key;
    private Long sequence;

    public AutoIncrementSequence(String key, Long sequence) {
        this.key = key;
        this.sequence = sequence;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

}
