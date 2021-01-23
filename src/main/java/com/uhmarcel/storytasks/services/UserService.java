package com.uhmarcel.storytasks.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    // TODO: Work on better exceptions / exception handling
    public static final UUID getUserIdentifier() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
            String originalUserIdentifier = token.getTokenAttributes().get("uid").toString();
            return UUID.nameUUIDFromBytes(originalUserIdentifier.getBytes());
        }
        return null;
    }

}
