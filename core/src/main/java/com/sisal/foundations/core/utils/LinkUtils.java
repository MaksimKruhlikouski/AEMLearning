package com.sisal.foundations.core.utils;

import com.sisal.foundations.core.services.SisalConfigService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Utility class for handling link operations and domain resolution.
 */
public class LinkUtils {
    
    private static final Logger LOG = LoggerFactory.getLogger(LinkUtils.class);
    
    private static SisalConfigService sisalConfigService;
    
    /**
     * Set the SisalConfigService instance (used for testing and dependency injection).
     * 
     * @param configService the config service instance
     */
    public static void setSisalConfigService(SisalConfigService configService) {
        sisalConfigService = configService;
    }
    
    /**
     * Gets the appropriate domain based on the current environment.
     * 
     * @param externalizerDomains map of available externalizer domains
     * @param originalPath the original path that might contain domain information
     * @return the appropriate domain ("author" or "publish")
     */
    public static String getDomain(Map<String, String> externalizerDomains, String originalPath) {
        // ✅ FIXED: Now uses environment-based domain determination
        return getDomainFixed(externalizerDomains, originalPath);
    }
    
    /**
     * Gets the appropriate domain based on the current environment (FIXED VERSION).
     * 
     * @param externalizerDomains map of available externalizer domains
     * @param originalPath the original path that might contain domain information
     * @return the appropriate domain ("author" or "publish")
     */
    public static String getDomainFixed(Map<String, String> externalizerDomains, String originalPath) {
        // Extract domain from original path if available
        String originalPathDomain = extractDomainFromPath(originalPath);
        if (StringUtils.isNotBlank(originalPathDomain)) {
            return originalPathDomain;
        }
        
        // Determine default domain based on current environment
        String defaultDomain = "publish"; // fallback default
        
        if (sisalConfigService != null) {
            try {
                boolean isPublish = sisalConfigService.isPublishEnvironment();
                defaultDomain = isPublish ? "publish" : "author";
                LOG.debug("Environment detection: isPublish={}, defaultDomain={}", isPublish, defaultDomain);
            } catch (Exception e) {
                LOG.warn("Failed to determine environment, using fallback domain: {}", defaultDomain, e);
            }
        } else {
            LOG.warn("SisalConfigService not available, using fallback domain: {}", defaultDomain);
        }
        
        return defaultDomain;
    }
    
    /**
     * Extracts domain information from the given path.
     * 
     * @param path the path to analyze
     * @return the domain if found, null otherwise
     */
    private static String extractDomainFromPath(String path) {
        if (StringUtils.isBlank(path)) {
            return null;
        }
        
        // Remove hardcoded localhost checks as requested
        // Simple domain extraction logic - can be enhanced based on actual requirements
        if (path.contains("://")) {
            try {
                String[] parts = path.split("://");
                if (parts.length > 1) {
                    String hostPart = parts[1].split("/")[0];
                    // You can add more sophisticated domain mapping logic here
                    return null; // For now, let environment detection handle it
                }
            } catch (Exception e) {
                LOG.debug("Failed to extract domain from path: {}", path, e);
            }
        }
        
        return null;
    }
}