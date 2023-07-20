package com.mysite.core.services.impl;

import com.day.cq.tagging.JcrTagManagerFactory;
import com.day.cq.tagging.TagManager;
import com.mysite.core.services.TagManagerService;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.jcr.Session;

import java.util.Objects;

import static org.mockito.Mockito.*;

@ExtendWith(AemContextExtension.class)
public class TagManagerServiceTest {

//    @Mock
//    private ResourceResolverFactory resolverFactory;
//
//    @Mock
//    private JcrTagManagerFactory jcrTagManagerFactory;

    private final AemContext context = new AemContext();

    @InjectMocks
    private TagManagerService tagManagerService;

    @BeforeEach
    public void setUp() {
        // Mocking with Mockito directly
        ResourceResolverFactory resolverFactory = mock(ResourceResolverFactory.class);
        JcrTagManagerFactory jcrTagManagerFactory = mock(JcrTagManagerFactory.class);
        ResourceResolver mockResolver = mock(ResourceResolver.class);
        Session mockSession = mock(Session.class);
        TagManager mockTagManager = mock(TagManager.class);

        try {
            when(resolverFactory.getServiceResourceResolver(any())).thenReturn(mockResolver);
            when(mockResolver.adaptTo(Session.class)).thenReturn(mockSession);
            when(jcrTagManagerFactory.getTagManager(mockSession)).thenReturn(mockTagManager);
        } catch (Exception e) {
            System.out.println(e);
        }
        context.registerService(ResourceResolverFactory.class, resolverFactory);
        context.registerService(JcrTagManagerFactory.class, jcrTagManagerFactory);

        tagManagerService = context.registerInjectActivateService(new TagManagerServiceImpl());
    }


    @Test
    public void testCreateGivenTag() {
        tagManagerService.createGivenTag("/content/tagpath", "Test Tag", "Tag Description");

        // Here you could add validations e.g.
        // Verify if session.save() was called once
        Session mockSession = context.resourceResolver().adaptTo(Session.class);

        if(Objects.isNull(mockSession)) {
            System.out.println("nulllllll");
        }
        else {
            System.out.println("gooood");
        }
        try {
            Mockito.verify(mockSession, times(1)).save();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // ... you could add more tests e.g. for exception scenarios
}
