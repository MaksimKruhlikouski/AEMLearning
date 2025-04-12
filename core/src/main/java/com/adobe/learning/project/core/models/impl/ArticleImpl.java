package com.adobe.learning.project.core.models.impl;

import com.adobe.learning.project.core.models.Article;
import com.adobe.learning.project.core.models.ArticleItem;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class}, adapters = Article.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ArticleImpl implements Article {
    @ChildResource
    private List<ArticleItem> articles;

    @Override
    public List<ArticleItem> getArticles() {
        return articles != null ? articles : Collections.emptyList();
    }
}
