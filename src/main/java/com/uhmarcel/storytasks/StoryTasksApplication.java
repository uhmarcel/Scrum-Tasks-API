package com.uhmarcel.storytasks;

import com.uhmarcel.storytasks.models.StoryItem;
import com.uhmarcel.storytasks.models.common.Priority;
import com.uhmarcel.storytasks.models.common.Size;
import com.uhmarcel.storytasks.models.common.Status;
import com.uhmarcel.storytasks.repositories.StoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class StoryTasksApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoryTasksApplication.class, args);
	}

}
