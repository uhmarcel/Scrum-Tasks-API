package com.uhmarcel.storytasks.repositories.events;

import com.uhmarcel.storytasks.models.StoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;

public class StoryItemEventListener extends AbstractMongoEventListener<Object> {

//    @Autowired
//    private MongoOperations mongoOperations;

//    @Override
//    public void onBeforeConvert(BeforeConvertEvent<Object> event) { // TODO: Move auto-increase to here
//        Object source = event.getSource();
//        if (source instanceof StoryItem) {
//            StoryItem story = (StoryItem) source;
//            System.out.println("HERE!!");
//            System.out.println(story.toString());
//        }
//
//    }



}
