package com.uhmarcel.storytasks.configuration;

import com.uhmarcel.storytasks.repositories.events.StoryItemEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfiguration {

    @Bean
    public StoryItemEventListener storyItemEventListener() {
        return new StoryItemEventListener();
    }

}
