package com.adobe.learning.project.core.models.impl;

import com.adobe.learning.project.core.models.Book;
import com.adobe.learning.project.core.models.BookModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.ExporterOption;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;
import java.util.List;

@Model(adaptables = Resource.class,
        adapters = BookModel.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
        resourceType = "learning-project/components/book")
@Exporter(name = "jackson", extensions = "json", selector = "book", options = {
        @ExporterOption(name = "MapperFeature.SORT_PROPERTIES_ALPHABETICALLY", value = "true")
})
public class BookModelImpl implements BookModel {
    @ValueMapValue
    private String title;

    @ValueMapValue
    private String firstname;

    @ValueMapValue
    private String lastname;

    @Inject
    private List<Book> books;

    @Override
    @JsonIgnore
    public String getTitle() {
        return title;
    }
    @Override
    public String getFirstname() {
        return firstname;
    }
    @Override
    public String getLastname() {
        return lastname;
    }
    @Override
    public List<Book> getBooks() {
        return books;
    }

    @JsonProperty(value = "JsonBooks")
    public String JSONBooks() {
        return "This is a JSON representation of the books";
    }
}
