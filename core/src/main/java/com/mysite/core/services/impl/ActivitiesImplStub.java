package com.mysite.core.services.impl;

import com.day.cq.commons.RangeIterator;
import com.day.cq.tagging.JcrTagManagerFactory;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.mysite.core.services.Activities;
import com.mysite.core.services.TagManagerService;
import com.mysite.core.utils.ResolverUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component(
        service = { TagManagerService.class }
)
public class ActivitiesImplStub implements TagManagerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagManagerServiceImpl.class);

//    @Reference
//    private ResourceResolverFactory resolverFactory;

    @Reference
    private JcrTagManagerFactory jcrTagManagerFactory;


    public void createGivenTag(String tagPath, String tagTitle, String tagDescription) {
        LOGGER.error("Error while retrieving tags");
    }


    public void retrieveTags(String resourcePath) {
        LOGGER.error("Error while retrieving tags");
    }


    public List<String> retrieveCFbyTags(String cfPath, String[] tag) {

        List<String> path_list = new ArrayList<>();

        // Adding elements to the list
        path_list.add("Hello");
        path_list.add("World");
        path_list.add("Java");

        return path_list;
    }
}
