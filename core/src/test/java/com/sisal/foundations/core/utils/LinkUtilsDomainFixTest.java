package com.sisal.foundations.core.utils;

import com.sisal.foundations.core.services.SisalConfigService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Integration test demonstrating the fix for the LinkUtils getDomain issue.
 * This test shows that the domain is now correctly determined based on environment
 * rather than always returning "publish".
 */
@ExtendWith(MockitoExtension.class)
class LinkUtilsDomainFixTest {

    @Mock
    private SisalConfigService sisalConfigService;

    @Test
    void demonstrateDomainFix_AuthorEnvironment() {
        // Given: We're in an author environment
        when(sisalConfigService.isPublishEnvironment()).thenReturn(false);
        LinkUtils.setSisalConfigService(sisalConfigService);
        
        Map<String, String> externalizerDomains = new HashMap<>();
        externalizerDomains.put("author", "http://author.example.com");
        externalizerDomains.put("publish", "http://publish.example.com");

        // When: We call getDomain
        String result = LinkUtils.getDomain(externalizerDomains, "/content/test");

        // Then: We get "author" instead of the old hardcoded "publish"
        assertEquals("author", result, 
            "In author environment, getDomain should return 'author' not 'publish'");
    }

    @Test
    void demonstrateDomainFix_PublishEnvironment() {
        // Given: We're in a publish environment
        when(sisalConfigService.isPublishEnvironment()).thenReturn(true);
        LinkUtils.setSisalConfigService(sisalConfigService);
        
        Map<String, String> externalizerDomains = new HashMap<>();
        externalizerDomains.put("author", "http://author.example.com");
        externalizerDomains.put("publish", "http://publish.example.com");

        // When: We call getDomain
        String result = LinkUtils.getDomain(externalizerDomains, "/content/test");

        // Then: We get "publish" as expected
        assertEquals("publish", result, 
            "In publish environment, getDomain should return 'publish'");
    }

    @Test
    void demonstrateNoMoreHardcodedLocalhost() {
        // Given: A path that might have contained localhost references
        when(sisalConfigService.isPublishEnvironment()).thenReturn(false);
        LinkUtils.setSisalConfigService(sisalConfigService);
        
        Map<String, String> externalizerDomains = new HashMap<>();

        // When: We call getDomain with a localhost path
        String result = LinkUtils.getDomain(externalizerDomains, "http://localhost:4502/content/test");

        // Then: The domain is determined by environment, not hardcoded localhost logic
        assertEquals("author", result, 
            "Domain should be determined by environment, not localhost detection");
    }
}