package com.mysite.core.services.impl;

import com.day.cq.tagging.JcrTagManagerFactory;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

import com.mysite.core.services.TagManagerService;
import com.mysite.core.utils.ResolverUtil;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;

@Component(service = TagManagerService.class, immediate = true)
public class TagManagerServiceImpl implements TagManagerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagManagerServiceImpl.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private JcrTagManagerFactory jcrTagManagerFactory;

    public void createTag(String tagPath, String tagTitle, String tagDescription) {
        ResourceResolver resourceResolver = null;

        LOGGER.info("createTag_createTag_createTag: " + tagPath);

        try {
            // Get the ResourceResolver
            resourceResolver = ResolverUtil.newResolver(resourceResolverFactory);

            // Get the current Session
            final Session session = resourceResolver.adaptTo(Session.class);

            TagManager tagManager = jcrTagManagerFactory.getTagManager(session);

            if(tagManager.canCreateTag(tagPath)) {
                LOGGER.info("can Create");
            }
            else {
                LOGGER.info("cannot Create");
            }

            if (session != null) {
                // Get the TagManager

                // Create the tag
                Tag tag = tagManager.createTag(tagPath, tagTitle, tagDescription, true);

                // Log tag ID for confirmation
                LOGGER.info("Tag created with ID: " + tag.getTagID());
                LOGGER.info("Tag created with ID_2");

                // Save the session to persist changes
                session.save();
            }

        } catch (Exception e) {
            LOGGER.error("Error while creating tag", e);
        } finally {
            LOGGER.error("finally donne");
            if (resourceResolver != null && resourceResolver.isLive()) {
                resourceResolver.close();
            }
        }
    }
}
