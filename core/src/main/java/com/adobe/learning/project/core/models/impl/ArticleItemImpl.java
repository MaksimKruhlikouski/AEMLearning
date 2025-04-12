package com.adobe.learning.project.core.models.impl;

import com.adobe.learning.project.core.models.ArticleItem;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


@Model(adaptables = Resource.class,
        adapters = ArticleItem.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ArticleItemImpl implements ArticleItem {
    @ValueMapValue
    private String name;

    @ValueMapValue
    private String path;

    @ValueMapValue
    private String description;

    @ValueMapValue
    private String image;

    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getPath() {
        return path;
    }
    @Override
    public String getDescription() {
        return description;
    }
    @Override
    public String getImage() {
        return image;
    }
}
