package com.sisal.foundations.core.services;

/**
 * Service interface for Sisal configuration and environment detection.
 */
public interface SisalConfigService {
    
    /**
     * Determines if the current environment is a publish environment.
     * 
     * @return true if this is a publish environment, false if it's an author environment
     */
    boolean isPublishEnvironment();
}