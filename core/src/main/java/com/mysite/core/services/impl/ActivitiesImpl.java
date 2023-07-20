package com.mysite.core.services.impl;

import java.util.Random;


import com.mysite.core.services.Activities;
import org.osgi.service.component.annotations.Component;

@Component(
        service = { Activities.class }
)
public class ActivitiesImpl implements Activities {

    public static final String[] ACTIVITIES = new String[] {
            "Camping", "Skiing",  "Skateboarding"
    };

    //private final int randomIndex = new Random().nextInt(ACTIVITIES.length);
    private final Random random = new Random();

    /**
     * @return the name of a random WKND adventure activity
     */
    public String getRandomActivity() {
        int randomIndex = random.nextInt(ACTIVITIES.length);
        return ACTIVITIES[randomIndex];
    }
}
