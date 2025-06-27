package com.sisal.foundations.core.services.impl;

import com.sisal.foundations.core.services.SisalConfigService;
import org.apache.sling.settings.SlingSettingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SisalConfigServiceImplTest {

    @Mock
    private SlingSettingsService slingSettingsService;

    @InjectMocks
    private SisalConfigServiceImpl sisalConfigService;

    @Test
    void testIsPublishEnvironment_WithPublishRunMode() {
        // Given
        when(slingSettingsService.getRunModes()).thenReturn(Set.of("publish", "prod"));

        // When
        boolean result = sisalConfigService.isPublishEnvironment();

        // Then
        assertTrue(result);
    }

    @Test
    void testIsPublishEnvironment_WithAuthorRunMode() {
        // Given
        when(slingSettingsService.getRunModes()).thenReturn(Set.of("author", "dev"));

        // When
        boolean result = sisalConfigService.isPublishEnvironment();

        // Then
        assertFalse(result);
    }

    @Test
    void testIsPublishEnvironment_WithNoSpecificRunMode() {
        // Given
        when(slingSettingsService.getRunModes()).thenReturn(Set.of("dev", "local"));

        // When
        boolean result = sisalConfigService.isPublishEnvironment();

        // Then
        assertFalse(result);
    }

    @Test
    void testIsPublishEnvironment_WithException() {
        // Given
        when(slingSettingsService.getRunModes()).thenThrow(new RuntimeException("Service unavailable"));

        // When
        boolean result = sisalConfigService.isPublishEnvironment();

        // Then
        assertFalse(result); // Should default to false (author) on error
    }
}