package com.adobe.learning.project.core.models.impl;

import com.adobe.learning.project.core.models.Book;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, adapters = Book.class)
public class BookImpl implements Book {
    @ValueMapValue
    private String name;

    @ValueMapValue
    private String dateOfCreation;

    @Override
    public String getName() { return name; }
    @Override
    public String getDateOfCreation() { return dateOfCreation; }
}
