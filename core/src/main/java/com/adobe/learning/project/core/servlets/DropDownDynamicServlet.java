package com.adobe.learning.project.core.servlets;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.methods=GET",
                "sling.servlet.resourceTypes=learning-project/dropdown/datasource"
        }
)
public class DropDownDynamicServlet extends SlingSafeMethodsServlet {

    private static final Logger log = LoggerFactory.getLogger(DropDownDynamicServlet.class);

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        try {
            ResourceResolver resolver = request.getResourceResolver();
            List<Resource> resources = new ArrayList<>();

            createOption(resolver, resources, "Dynamic Option 1", "option1");
            createOption(resolver, resources, "Dynamic Option 2", "option2");
            createOption(resolver, resources, "Dynamic Option 3", "option3");

            DataSource dataSource = new SimpleDataSource(resources.iterator());
            request.setAttribute(DataSource.class.getName(), dataSource);
        } catch (Exception e) {
            log.error("Error creating dropdown DataSource", e);
        }
    }

    private void createOption(ResourceResolver resolver, List<org.apache.sling.api.resource.Resource> resources, String text, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put("text", text);
        map.put("value", value);

        ValueMap vm = new ValueMapDecorator(map);
        resources.add(new ValueMapResource(resolver, new ResourceMetadata(), "nt:unstructured", vm));
    }
}
