package com.adobe.learning.project.core.models.impl;

import com.adobe.learning.project.core.models.AllDataSly;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.ArrayList;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, adapters = AllDataSly.class)
public class AllDataSlyModel implements AllDataSly {

    @ValueMapValue
    private String name;

    @ValueMapValue
    private String attributeName;

    @ValueMapValue
    private String elementName;

    @ValueMapValue
    private String testName;

    @ValueMapValue
    private List<String> listNames;

    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getAttributeName() {
        return attributeName;
    }
    @Override
    public String getElementName() {
        return elementName;
    }
    @Override
    public String getTestName() {
        return testName;
    }
    @Override
    public List<String> getListNames() {
        List<String> names = new ArrayList<>();
        if (listNames != null) {
            names.addAll(listNames);
        }
        return names;
    }
}
