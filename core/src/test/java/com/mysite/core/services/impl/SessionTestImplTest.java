package com.mysite.core.services.impl;

import com.mysite.core.services.Activities;
import com.mysite.core.services.SessionTest;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.jcr.Node;
import javax.jcr.Session;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(AemContextExtension.class)
class SessionTestImplTest {

    private final AemContext context = new AemContext();

    @Mock
    private ResourceResolverFactory mockResourceResolverFactory;

    @Mock
    private ResourceResolver mockResourceResolver;

    @Mock
    private Session mockSession;

    @Mock
    private Node rootNode;

    @Mock
    private Node nodeContainer;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        context.registerService(ResourceResolverFactory.class, mockResourceResolverFactory);

        // Mocking the behavior of resolverFactory to return mockResourceResolver
        when(mockResourceResolverFactory.getResourceResolver(anyMap())).thenReturn(mockResourceResolver);

        // Mocking the behavior of resourceResolver to return mockSession
        when(mockResourceResolver.adaptTo(Session.class)).thenReturn(mockSession);

        // Mocking Nodes
        when(mockSession.getRootNode()).thenReturn(rootNode);
        when(rootNode.getNode(anyString())).thenReturn(nodeContainer);

        // Registering the service under test
        context.registerInjectActivateService(new SessionTestImpl());
    }

    @Test
    void testSetRandomNode() throws Exception {
        // Get the OSGi service
        SessionTest sessionTest = context.getService(SessionTest.class);

        // Invoking the service method
        sessionTest.setRandomNode();

        // Assertions
//        verify(mockSession).save(); // Verify session.save() was called
//        verify(nodeContainer).addNode("customAdd"); // Verify that the node was added

        verify(mockResourceResolver).adaptTo(Session.class);

    }
}
