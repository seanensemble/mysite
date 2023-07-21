package com.mysite.core.services.impl;

import com.mysite.core.services.Activities;
import com.mysite.core.services.SessionTest;
import com.mysite.core.utils.ResolverUtil;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;
import java.util.Random;

@Component(
        service = { SessionTest.class }
)
public class SessionTestImpl implements SessionTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagManagerServiceImpl.class);

    @Reference
    private ResourceResolverFactory resolverFactory;

    /**
     * @return the name of a random WKND adventure activity
     */
    public String setRandomNode() {
        ResourceResolver resourceResolver = null;

        try {
            resourceResolver = ResolverUtil.newResolver(resolverFactory);
            Session session = resourceResolver.adaptTo(Session.class);

            Node root = session.getRootNode();
            Node nodeContainer = root.getNode("content/cq:tags/example-namespace/example-tag3");

            Node customNode = nodeContainer.addNode("customAdd");
            customNode.setProperty("message", "Adobe CQ is part of the Adobe Digital Marketing Suite!");


            session.save();
            session.logout();

        } catch (Exception e) {
            LOGGER.error("Error while creating tag", e);
        } finally {
            LOGGER.error("finally donne");
        }

        return "title";
    }
}
