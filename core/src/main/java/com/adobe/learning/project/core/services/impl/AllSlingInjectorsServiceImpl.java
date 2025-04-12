package com.adobe.learning.project.core.services.impl;

import com.adobe.learning.project.core.services.AllSlingInjectorsService;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.List;

@Component(service = AllSlingInjectorsService.class)
public class AllSlingInjectorsServiceImpl implements AllSlingInjectorsService {

    @Override
    public List<String> getPagesTitleService(Resource resourcePath) {
        List<String> titles = new ArrayList<>();
        if (resourcePath != null) {
            for (Resource child : resourcePath.getChildren()) {
                Page page = child.adaptTo(Page.class);
                if (page != null) {
                    titles.add(page.getTitle());
                }
            }
        }
        return titles;
    }
}
