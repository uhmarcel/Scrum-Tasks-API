package com.uhmarcel.storytasks.controllers;

import com.uhmarcel.storytasks.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.uhmarcel.storytasks.configuration.ControllerConstants.APP_ROUTE_PREFIX;

@RestController
@RequestMapping(APP_ROUTE_PREFIX)
public class UserController {

    @Autowired
    private UserRepository userRepository;
    private Logger log = LoggerFactory.getLogger(StoryItemController.class);

}
