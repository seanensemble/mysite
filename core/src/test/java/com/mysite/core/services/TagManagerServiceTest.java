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

    private static final Logger LOGGER = LoggerFactory.getLogger(TagManagerServiceTest.class);

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

//    private TagManagerServiceImpl tagManagerService;

    @Mock
    Helper1 helper;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);



        // Mocking ResolverUtil static call. You will need PowerMock to mock static methods.
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
        // Given
        String tagPath = "some/tagPath";
        String tagTitle = "TagTitle";
        String tagDescription = "TagDescription";


        // When
        tagManagerService.createTag(tagPath, tagTitle, tagDescription);

        LOGGER.info("inside testCreateTag");
        System.out.println("inside testCreateTag println");

        // Then
        try {
            LOGGER.info("inside try bloc");
            LOGGER.info(mockingDetails(session).printInvocations());
            System.out.println(mockingDetails(session).printInvocations());
            System.out.println(mockingDetails(tagManager).printInvocations());
            System.out.println(mockingDetails(helper).printInvocations());


            verify(tagManager, times(1)).createTag(tagPath, tagTitle, tagDescription, true);
            verify(session, times(1)).save();
            verify(helper).getBaeldungString();
        } catch (Exception e) {
            fail("Expected no exceptions, but got: " + e.getMessage());
        }
//        verify(resourceResolver, times(1)).close();
    }



    @Test
    public void testSessionSave() {
        // Given
        String tagPath = "some/tagPath";
        String tagTitle = "TagTitle";
        String tagDescription = "TagDescription";

        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);

        // When
        tagManagerService.createTag(tagPath, tagTitle, tagDescription);

        // Then
        try {
            verify(session, times(1)).save();
        } catch (Exception e) {
            fail("Expected no exceptions, but got: " + e.getMessage());
        }
    }

}
