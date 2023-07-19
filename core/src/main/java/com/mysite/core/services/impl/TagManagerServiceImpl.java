package com.mysite.core.services.impl;

import com.day.cq.commons.RangeIterator;
import com.day.cq.tagging.JcrTagManagerFactory;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

import com.mysite.core.helper.Helper1;
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

@Component(service = TagManagerService.class, immediate = true)
public class TagManagerServiceImpl implements TagManagerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagManagerServiceImpl.class);

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Reference
    private JcrTagManagerFactory jcrTagManagerFactory;

    public void createTag(String tagPath, String tagTitle, String tagDescription) {
        ResourceResolver resourceResolver = null;

        LOGGER.info("createTag_createTag_createTag: " + tagPath);

        Helper1 helper = new Helper1();

        try {
            // Get the ResourceResolver
            resourceResolver = ResolverUtil.newResolver(resolverFactory);

            // Get the current Session
            final Session session = resourceResolver.adaptTo(Session.class);

            TagManager tagManager = jcrTagManagerFactory.getTagManager(session);

            if(tagManager.canCreateTag(tagPath)) {
                LOGGER.info("can Create");
            }
            else {
                LOGGER.info("cannot Create");
            }

            helper.getBaeldungString();

//            if (session != null) {
                // Get the TagManager

                // Create the tag
                Tag tag = tagManager.createTag(tagPath, tagTitle, tagDescription, true);

                // Log tag ID for confirmation
                LOGGER.info("Tag created with ID: " + tag.getTagID());
                LOGGER.info("Tag created with ID_2");

                // Save the session to persist changes
                session.save();
//            }

        } catch (Exception e) {
            LOGGER.error("Error while creating tag", e);
        } finally {
            LOGGER.error("finally donne");
            if (resourceResolver != null && resourceResolver.isLive()) {
                resourceResolver.close();
            }
        }
    }


    public void retrieveTags(String resourcePath) {
        ResourceResolver resolver = null;
        LOGGER.info("RetrieveTags called with path: " + resourcePath);

        try {
            // Get the ResourceResolver
            resolver = ResolverUtil.newResolver(resolverFactory);

            // Get the current Session
            Session session = resolver.adaptTo(Session.class);

            if (session != null) {
                // Get the TagManager
                TagManager tagManager = jcrTagManagerFactory.getTagManager(session);

                // Get the Resource
                Resource resource = resolver.getResource(resourcePath);

                // Get the tags
                if (resource != null) {
//                    tagManager.getTags(resolver.getResource("/content/dam/mysite/asset.jpg/jcr:content/metadata"))[0].getTitle()

//                    tagManager.getTags(resolver.getResource("/content/dam/testground/content-fragments/en/author-details/jcr:content/data/master"))[0].getTitle()

                    Tag[] tags = tagManager.getTags(resource);
                    Arrays.stream(tags).forEach(tag -> LOGGER.info("Found tag: " + tag.getTitle()));
                } else {
                    LOGGER.info("Resource at path " + resourcePath + " not found.");
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error while retrieving tags", e);
        } finally {
            if (resolver != null && resolver.isLive()) {
                resolver.close();
            }
        }
    }


    public List<String> retrieveCFbyTags(String cfPath, String[] tag) {
        ResourceResolver resolver = null;

        List<String> path_list = new ArrayList<>();
        try {
            // Get the ResourceResolver
            resolver = ResolverUtil.newResolver(resolverFactory);

            // Get the current Session
            Session session = resolver.adaptTo(Session.class);

            if (session != null) {
                // Get the TagManager
                TagManager tagManager = jcrTagManagerFactory.getTagManager(session);

                RangeIterator<Resource> iterator = tagManager.find(cfPath, tag);

                while (iterator.hasNext()) {
                    Resource item = iterator.next();
                    String path = item.getPath();
                    path_list.add(path);
                }
            }

            String bob = "bob";

        } catch (Exception e) {
            LOGGER.error("Error while retrieving tags", e);
        } finally {
            if (resolver != null && resolver.isLive()) {
                resolver.close();
            }
        }

        return path_list;
    }
}
