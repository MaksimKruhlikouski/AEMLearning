package com.sisal.foundations.core.utils;

import com.sisal.foundations.core.services.SisalConfigService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LinkUtilsTest {

    @Mock
    private SisalConfigService sisalConfigService;

    private Map<String, String> externalizerDomains;

    @BeforeEach
    void setUp() {
        externalizerDomains = new HashMap<>();
        externalizerDomains.put("author", "http://localhost:4502");
        externalizerDomains.put("publish", "http://localhost:4503");
        
        // Set up the service for testing
        LinkUtils.setSisalConfigService(sisalConfigService);
    }

    @Test
    void testGetDomainFixed_PublishEnvironment() {
        // Given
        when(sisalConfigService.isPublishEnvironment()).thenReturn(true);

        // When
        String result = LinkUtils.getDomainFixed(externalizerDomains, "");

        // Then
        assertEquals("publish", result);
    }

    @Test
    void testGetDomainFixed_AuthorEnvironment() {
        // Given
        when(sisalConfigService.isPublishEnvironment()).thenReturn(false);

        // When
        String result = LinkUtils.getDomainFixed(externalizerDomains, "");

        // Then
        assertEquals("author", result);
    }

    @Test
    void testGetDomainFixed_WithServiceException() {
        // Given
        when(sisalConfigService.isPublishEnvironment()).thenThrow(new RuntimeException("Service error"));

        // When
        String result = LinkUtils.getDomainFixed(externalizerDomains, "");

        // Then
        assertEquals("publish", result); // Should use fallback default
    }

    @Test
    void testGetDomainFixed_WithNullService() {
        // Given
        LinkUtils.setSisalConfigService(null);

        // When
        String result = LinkUtils.getDomainFixed(externalizerDomains, "");

        // Then
        assertEquals("publish", result); // Should use fallback default
    }

    @Test
    void testGetDomainFixed_WithPathContainingDomain() {
        // Given
        when(sisalConfigService.isPublishEnvironment()).thenReturn(true);
        // For this test, we'll assume extractDomainFromPath returns null
        // as the current implementation doesn't extract domains from paths

        // When
        String result = LinkUtils.getDomainFixed(externalizerDomains, "http://example.com/content/test");

        // Then
        assertEquals("publish", result); // Should use environment-based determination
    }

    @Test
    void testGetDomainFixed_WithBlankPath() {
        // Given
        when(sisalConfigService.isPublishEnvironment()).thenReturn(false);

        // When
        String result = LinkUtils.getDomainFixed(externalizerDomains, "");

        // Then
        assertEquals("author", result);
    }

    @Test
    void testGetDomainFixed_WithNullPath() {
        // Given
        when(sisalConfigService.isPublishEnvironment()).thenReturn(false);

        // When
        String result = LinkUtils.getDomainFixed(externalizerDomains, null);

        // Then
        assertEquals("author", result);
    }

    @Test
    void testGetDomain_PublicMethod_AuthorEnvironment() {
        // Given
        when(sisalConfigService.isPublishEnvironment()).thenReturn(false);

        // When
        String result = LinkUtils.getDomain(externalizerDomains, "");

        // Then
        assertEquals("author", result);
    }

    @Test
    void testGetDomain_PublicMethod_PublishEnvironment() {
        // Given
        when(sisalConfigService.isPublishEnvironment()).thenReturn(true);

        // When
        String result = LinkUtils.getDomain(externalizerDomains, "");

        // Then
        assertEquals("publish", result);
    }
}