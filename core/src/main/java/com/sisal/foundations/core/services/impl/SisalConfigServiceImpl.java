package com.sisal.foundations.core.services.impl;

import com.sisal.foundations.core.services.SisalConfigService;
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Implementation of SisalConfigService that determines environment type
 * based on Sling run modes.
 */
@Component(service = SisalConfigService.class, immediate = true)
public class SisalConfigServiceImpl implements SisalConfigService {
    
    private static final Logger LOG = LoggerFactory.getLogger(SisalConfigServiceImpl.class);
    private static final String PUBLISH_RUN_MODE = "publish";
    
    @Reference
    private SlingSettingsService slingSettingsService;
    
    @Override
    public boolean isPublishEnvironment() {
        try {
            Set<String> runModes = slingSettingsService.getRunModes();
            boolean isPublish = runModes.contains(PUBLISH_RUN_MODE);
            LOG.debug("Current run modes: {}, isPublish: {}", runModes, isPublish);
            return isPublish;
        } catch (Exception e) {
            LOG.error("Error determining environment type, defaulting to author", e);
            return false; // Default to author if we can't determine
        }
    }
}