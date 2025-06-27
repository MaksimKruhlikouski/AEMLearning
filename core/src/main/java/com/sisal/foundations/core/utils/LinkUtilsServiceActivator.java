package com.sisal.foundations.core.utils;

import com.sisal.foundations.core.services.SisalConfigService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OSGi Component to properly inject the SisalConfigService into LinkUtils.
 * This ensures the service is available for static utility methods.
 */
@Component(service = LinkUtilsServiceActivator.class, immediate = true)
public class LinkUtilsServiceActivator {
    
    private static final Logger LOG = LoggerFactory.getLogger(LinkUtilsServiceActivator.class);
    
    @Reference
    private SisalConfigService sisalConfigService;
    
    @Activate
    protected void activate() {
        LinkUtils.setSisalConfigService(sisalConfigService);
        LOG.info("LinkUtils configured with SisalConfigService");
    }
}