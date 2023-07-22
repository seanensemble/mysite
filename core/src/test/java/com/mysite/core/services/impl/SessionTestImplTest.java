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
import static org.mockito.Mockito.*;
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
    private ResourceResolverFactory mockResolverFactory;

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
        // Initialize the mocks
        MockitoAnnotations.openMocks(this);

        // Mock the behavior for the resolverFactory
        when(mockResolverFactory.getServiceResourceResolver(any())).thenReturn(mockResourceResolver);

        // Mock the behavior for the resourceResolver
        when(mockResourceResolver.adaptTo(Session.class)).thenReturn(mockSession);

        // Mock the behavior for session.getRootNode() and other node operations
        when(mockSession.getRootNode()).thenReturn(rootNode);
        when(rootNode.getNode("content/cq:tags/example-namespace/example-tag3")).thenReturn(nodeContainer);
        when(nodeContainer.addNode("customAdd2")).thenReturn(mock(Node.class));

        // Register the service with the mock
        context.registerService(ResourceResolverFactory.class, mockResolverFactory);
        context.registerInjectActivateService(new SessionTestImpl());
    }

    @Test
    void testSetRandomNode() throws Exception {
        // Get the OSGi service
        SessionTest sessionTest = context.getService(SessionTest.class);

        // Call the method we want to test
        sessionTest.setRandomNode();

        // Verify that the session.save() method was called
//        verify(mockSession, times(1)).save();

        // Verify that the node was added
        verify(nodeContainer, times(1)).addNode("customAdd2");
    }
}
