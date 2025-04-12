package com.adobe.learning.project.core.services;

import org.apache.sling.api.resource.Resource;

import java.util.List;

public interface AllSlingInjectorsService {
    List<String> getPagesTitleService(Resource resourcePath);
}
