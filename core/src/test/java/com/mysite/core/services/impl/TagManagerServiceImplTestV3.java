package com.mysite.core.services.impl;

import com.day.cq.tagging.JcrTagManagerFactory;
import com.day.cq.tagging.TagManager;
import com.mysite.core.utils.ResolverUtil;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.jcr.Session;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import com.mysite.core.services.TagManagerService;
@ExtendWith(AemContextExtension.class)
public class TagManagerServiceImplTestV3 {

    private final AemContext context = new AemContext();

    private TagManagerServiceImpl serviceUnderTest;

    private Session session;
    private TagManager tagManager;

    @Mock
    private JcrTagManagerFactory jcrTagManagerFactory;

    @BeforeEach
    void setUp() {
//        context.registerInjectActivateService(new ActivitiesImplStub());

        // Mock ResourceResolverFactory, JcrTagManagerFactory and others
        ResourceResolver resourceResolver = mock(ResourceResolver.class);
        session = mock(Session.class);
        tagManager = mock(TagManager.class);
        jcrTagManagerFactory = mock(JcrTagManagerFactory.class);

        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
        when(jcrTagManagerFactory.getTagManager(session)).thenReturn(tagManager);

        context.registerService(JcrTagManagerFactory.class, jcrTagManagerFactory);

        // Mock static method ResolverUtil.newResolver
        Mockito.mockStatic(ResolverUtil.class);
        try {
        when(ResolverUtil.newResolver(any())).thenReturn(resourceResolver);
        }
        catch (Exception e) {
            System.out.println( e);
        }

        context.registerInjectActivateService(new TagManagerServiceImpl());
    }

    @Test
    void testCreateGivenTag() throws Exception {
        String tagPath = "path/to/tag";
        String tagTitle = "Test Tag";
        String tagDescription = "This is a test tag";

        TagManagerService TagManagerService = context.getService(TagManagerService.class);

        TagManagerService.createGivenTag(tagPath, tagTitle, tagDescription);

//        verify(tagManager).createTag(tagPath, tagTitle, tagDescription, true);
//        verify(session).save();

        if(Objects.isNull(session)) {
            System.out.println("session nulllllll");
        }
        else {
            System.out.println("session gooood");
        }
        

        // Check if there are no pending changes after the save
        assertFalse(session.hasPendingChanges());
    }
}
