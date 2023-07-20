package com.mysite.core.services;

import com.day.cq.tagging.JcrTagManagerFactory;
import com.day.cq.tagging.TagManager;
import com.mysite.core.helper.Helper1;
import com.mysite.core.services.impl.TagManagerServiceImpl;
import com.mysite.core.utils.ResolverUtil;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagManagerServiceTest {
    @InjectMocks
    private TagManagerServiceImpl tagManagerService;
    @Mock
    private ResourceResolverFactory resolverFactory;
    @Mock
    private JcrTagManagerFactory jcrTagManagerFactory;
    @Mock
    private Session session;
    @Mock
    private TagManager tagManager;
    @Mock
    private ResourceResolver resourceResolver;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        try {
            when(ResolverUtil.newResolver(resolverFactory)).thenReturn(resourceResolver);
            when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
            when(jcrTagManagerFactory.getTagManager(session)).thenReturn(tagManager);
        } catch (Exception e) {
            fail("Expected no exceptions, but got: " + e.getMessage());
        }
    }

    @Test
    public void testCreateTag() {
        String tagPath = "some/tagPath";
        String tagTitle = "TagTitle";
        String tagDescription = "TagDescription";

        tagManagerService.createTag(tagPath, tagTitle, tagDescription);

        try {
            verify(tagManager, times(1)).createTag(tagPath, tagTitle, tagDescription, true);

            System.out.println(mockingDetails(session).printInvocations()); // No interactions and stubbings found for mock: session

            verify(session, times(1)).save(); // <=== testing session.save();

        } catch (Exception e) {
            fail("Expected no exceptions, but got: " + e.getMessage());
        }
    }
}
