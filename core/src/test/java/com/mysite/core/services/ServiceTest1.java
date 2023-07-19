package com.mysite.core.services;

import com.mysite.core.helper.Helper1;
import com.mysite.core.services.impl.TagManagerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ServiceTest1 {
    @InjectMocks
    private TagManagerServiceImpl tagManagerService;

    @Mock
    private Helper1 helper;

    @Test
    public void testCreateTag() {
        // Arrange
        String mockReturnValue = "MockedString";
        Mockito.when(helper.getBaeldungString()).thenReturn(mockReturnValue);

        // Act
        tagManagerService.createTag("testPath", "testTitle", "testDescription");

        // Assert
        verify(helper, times(1)).getBaeldungString();
    }
}
