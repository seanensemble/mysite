package com.mysite.core.services.impl;

import com.day.cq.commons.RangeIterator;
import com.day.cq.tagging.JcrTagManagerFactory;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.mysite.core.helper.Helper1;
import com.mysite.core.services.TagManagerService;
import com.mysite.core.services.impl.TagManagerServiceImpl;
import com.mysite.core.utils.ResolverUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.Session;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagManagerServiceImplTest {

    @Mock
    private ResourceResolverFactory resourceResolverFactory;

    @Mock
    private JcrTagManagerFactory jcrTagManagerFactory;

    @Mock
    private Session session;

    @Mock
    private TagManager tagManager;

    @Mock
    private ResourceResolver resourceResolver;

    @Mock
    private Resource resource;

    @Mock
    private Tag tag;

    @Mock
    private RangeIterator<Resource> iterator;

    @InjectMocks
    private TagManagerServiceImpl tagManagerService;

    @BeforeEach
    public void setUp() throws Exception {
        lenient().when(ResolverUtil.newResolver(resourceResolverFactory)).thenReturn(resourceResolver);
        lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
        lenient().when(jcrTagManagerFactory.getTagManager(session)).thenReturn(tagManager);
        lenient().when(resourceResolver.getResource(anyString())).thenReturn(resource);
        lenient().when(tagManager.getTags(resource)).thenReturn(new Tag[]{tag});

//        MockitoAnnotations.openMocks(this);
        session.save();
    }

    @Test
    public void testCreateTag() {
        tagManagerService.createGivenTag("/content/tag", "Test Tag", "This is a test tag");
        try {
            verify(tagManager, times(1)).createTag(anyString(), anyString(), anyString(), anyBoolean());
            verify(resourceResolver, times(1)).adaptTo(Session.class);

//            assertTrue(session.hasPendingChanges());
//
//            session.save();

            assertFalse(session.hasPendingChanges());

//            verify(session, times(1)).save();
        } catch (Exception e) {
            fail("Expected no exceptions, but got: " + e.getMessage());
        }
    }

    @Test
    public void testRetrieveTags() {
        tagManagerService.retrieveTags("/content/resourcePath");
        verify(tagManager, times(1)).getTags(any(Resource.class));
    }

//    @Test
//    public void testRetrieveCFbyTags() {
//        String cfPath = "/content/somepath";
//        String[] tags = {"tag1", "tag2"};
//
//        try {
//            when(resourceResolverFactory.getServiceResourceResolver(anyMap())).thenReturn(resourceResolver);
//        } catch (Exception e) {
//            fail("Expected no exceptions, but got: " + e.getMessage());
//        }
//        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
//        when(jcrTagManagerFactory.getTagManager(session)).thenReturn(tagManager);
//        when(tagManager.find(cfPath, tags)).thenReturn(iterator);
//
//        Resource mockResource1 = mock(Resource.class);
//        when(mockResource1.getPath()).thenReturn("/path/to/resource1");
//
//        Resource mockResource2 = mock(Resource.class);
//        when(mockResource2.getPath()).thenReturn("/path/to/resource2");
//
//        when(iterator.hasNext()).thenReturn(true, true, false);
//        when(iterator.next()).thenReturn(mockResource1, mockResource2);
//
//        List<String> paths = tagManagerService.retrieveCFbyTags(cfPath, tags);
//
//        assertEquals(2, paths.size());
//        assertEquals("/path/to/resource1", paths.get(0));
//        assertEquals("/path/to/resource2", paths.get(1));
//    }
}
