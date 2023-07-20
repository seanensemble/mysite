package com.mysite.core.services.impl;

import com.mysite.core.services.Activities;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(AemContextExtension.class)
class ActivitiesImplTest {

    private final AemContext context = new AemContext();

    @BeforeEach
    void setUp() {
        // Register the OSGi service
        context.registerInjectActivateService(new ActivitiesImpl());
    }

    @Test
    void testGetRandomActivity() {
        // Get the OSGi service
        Activities activities = context.getService(Activities.class);

        Assertions.assertNotNull(activities, "Activities service should not be null.");

        Set<String> observedActivities = new HashSet<>();

        // Call the getRandomActivity method multiple times (let's say 1000 times)
        // to increase the probability of seeing all possible activities.
        for (int i = 0; i < 1000; i++) {
            String activity = activities.getRandomActivity();
            Assertions.assertNotNull(activity, "Returned activity should not be null.");
            observedActivities.add(activity);
        }

        // Ensure that all possible activities have been observed.
        for (String expectedActivity : ActivitiesImpl.ACTIVITIES) {
            Assertions.assertTrue(observedActivities.contains(expectedActivity),
                    "Expected to observe activity: " + expectedActivity);
        }
    }
}