package com.adobe.learning.project.core.models.impl;

import com.adobe.learning.project.core.models.AllSlingInjectors;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.*;
import com.adobe.learning.project.core.services.AllSlingInjectorsService;

import java.util.ArrayList;
import java.util.List;

@Model(adaptables = {SlingHttpServletRequest.class,Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, adapters = AllSlingInjectors.class)
public class AllSlingInjectorsImpl implements AllSlingInjectors {

    @ScriptVariable
    private Page currentPage;

    @ValueMapValue
    private String manualPageTitle;

    @ChildResource
    private Resource cars;

    @RequestAttribute
    private String attr;

    @ResourcePath(path = "/content/learning-project/us/en")
    private Resource resourcePath;

    @SlingObject
    private ResourceResolver resourceResolver;

    @OSGiService
    private AllSlingInjectorsService AllSlingInjectorsService;

    @Self
    @Via("resource")
    private Resource SelfResource;

    @Override
    public String getPageTitle() {
        return currentPage.getTitle();
    }
    @Override
    public String getManualPageTitle() {
        return manualPageTitle;
    }
    @Override
    public List<String> getCarInfo() {
        List<String> carInfoList = new ArrayList<>();

        if (cars != null) {
            for (Resource carResource : cars.getChildren()) {
                String brand = carResource.getValueMap().get("brand", String.class);
                String model = carResource.getValueMap().get("model", String.class);
                Integer year = carResource.getValueMap().get("year", Integer.class);

                if (brand != null && model != null && year != null) {
                    String carDetails = brand + " " + model + " (" + year + ")";
                    carInfoList.add(carDetails);
                }
            }
        }

        if (carInfoList.isEmpty()) {
            carInfoList.add("Cars are missing...");
        }

        return carInfoList;
    }
    @Override
    public String getAttr(){
        return attr;
    }
    @Override
    public List<String> getPagesTitle(){
        return AllSlingInjectorsService.getPagesTitleService(resourcePath);
    }


    @Override
    public String getTitleViaSelf(){
        if(SelfResource != null){
            return SelfResource.getValueMap().get("manualPageTitle", String.class);
        }
        return "Title missing...";
    }

    @Override
    public String getTitleViaRR(){
        if(resourceResolver != null){
            Resource r = resourceResolver.getResource("/content/learning-project/us/en/home");
            if(r != null){
                Resource cr = r.getChild("jcr:content");
                if(cr != null){
                    return cr.getValueMap().get("jcr:title", String.class);
                }
            }
        }
        return "Title missing...";
    }
}
