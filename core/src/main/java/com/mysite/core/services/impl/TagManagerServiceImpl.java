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

import javax.inject.Inject;
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

        try {
            resourceResolver = ResolverUtil.newResolver(resolverFactory);

            Session session = resourceResolver.adaptTo(Session.class);

            TagManager tagManager = jcrTagManagerFactory.getTagManager(session);

            if (session != null) {
                tagManager.createTag(tagPath, tagTitle, tagDescription, true);
                session.save(); //<==== Test this
            }

        } catch (Exception e) {
            LOGGER.error("Error while creating tag", e);
        } finally {
            if (resourceResolver != null && resourceResolver.isLive()) {
                resourceResolver.close();
            }
        }
    }
}
