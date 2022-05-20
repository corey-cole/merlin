package com.example.aviation.merlin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityController {
    
    final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    // This is a trivial example that we can use to provoke normal or abnormal log outputs
    @GetMapping("/activity/{id}")
    public String getActivityById(@PathVariable String id) {
        try {
            if (id.startsWith("a")) {
                logger.info("Everything is A-OK");
            }
            if (id.startsWith("z")) {
                throw new Exception("Yikes!  I never expected that.");
            }
        } catch (Exception ex) {
            logger.error("Something went wrong, ignoring and continuing", ex);
        }
        return id;
    }
}
