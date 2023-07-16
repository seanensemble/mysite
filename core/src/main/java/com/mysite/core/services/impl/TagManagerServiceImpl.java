package com.mysite.core.services.impl;

import com.day.cq.tagging.JcrTagManagerFactory;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;

@Component(service = TagManagerServiceImpl.class, immediate = true)
public class TagManagerServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagManagerServiceImpl.class);

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Reference
    private JcrTagManagerFactory jcrTagManagerFactory;

    public void createTag(String tagPath, String tagTitle, String tagDescription) {
        ResourceResolver resolver = null;

        LOGGER.info("createTag createTag createTag");
        LOGGER.error("createTag createTag createTag");

//        try {
//            // Get the ResourceResolver
//            resolver = resolverFactory.getServiceResourceResolver(null);
//
//            // Get the current Session
//            Session session = resolver.adaptTo(Session.class);
//
//            if (session != null) {
//                // Get the TagManager
//                TagManager tagManager = jcrTagManagerFactory.getTagManager(session);
//
//                // Create the tag
//                Tag tag = tagManager.createTag(tagPath, tagTitle, tagDescription, true);
//
//                // Log tag ID for confirmation
//                LOGGER.info("Tag created with ID: " + tag.getTagID());
//
//                // Save the session to persist changes
//                session.save();
//            }
//
//        } catch (Exception e) {
//            LOGGER.error("Error while creating tag", e);
//        } finally {
//            if (resolver != null && resolver.isLive()) {
//                resolver.close();
//            }
//        }
    }
}
