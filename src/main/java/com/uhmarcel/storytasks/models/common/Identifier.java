package com.uhmarcel.storytasks.models.common;

import com.uhmarcel.storytasks.services.UserService;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.UUID;

public class Identifier {

    private final UUID userId;
    private final Long referenceId;

    @PersistenceConstructor
    public Identifier(UUID userId, Long referenceId) {
        this.userId = userId != null ? userId : UserService.getUserIdentifier();
        this.referenceId = referenceId;
    }

    public Identifier(Long referenceId) {
        this.userId = UserService.getUserIdentifier();
        this.referenceId = referenceId;
    }

    public UUID getUserId() {
        return userId;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    @Override
    public String toString() {
        return "Identifier{" +
                "userId=" + userId +
                ", referenceId=" + referenceId +
                '}';
    }

    public static final Identifier from(Long referenceId) {
        return new Identifier(null, referenceId);
    }

    public static final Identifier from(UUID userId, Long referenceId) {
        return new Identifier(userId, referenceId);
    }

}

