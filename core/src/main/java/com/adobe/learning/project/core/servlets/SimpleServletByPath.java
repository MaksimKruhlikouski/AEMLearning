package com.adobe.learning.project.core.servlets;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.*;

@Component(service = {Servlet.class})
@SlingServletPaths(value = {"/bin/simpleServletByPath", "/boom/simpleServletByPath"})
public class SimpleServletByPath extends SlingAllMethodsServlet {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleServletByPath.class);

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) {
        final ResourceResolver resourceResolver = req.getResourceResolver();
        Page page = resourceResolver.adaptTo(PageManager.class).getPage("/content/learning-project/us/en");

        List<Map<String, String>> pagesList = new ArrayList<>();

        try {
            Iterator<Page> childPages = page.listChildren();
            while (childPages.hasNext()) {
                Page childPage = childPages.next();
                Map<String, String> pageMap = new HashMap<>();
                pageMap.put(childPage.getTitle(), childPage.getPath());
                pagesList.add(pageMap);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(pagesList);

            resp.setContentType("application/json");
            resp.getWriter().write(jsonString);
        } catch (Exception e) {
            LOG.info("\n ERROR {} ", e.getMessage());
        }
    }

    @Override
    protected void doPost(SlingHttpServletRequest req, SlingHttpServletResponse resp)
            throws IOException {
        try {
            List<RequestParameter> requestParameterList = req.getRequestParameterList();
            for (RequestParameter requestParameter : requestParameterList) {
                LOG.info("\n ===PARAMETERS===>  {} : {} ", requestParameter.getName(), requestParameter.getString());
            }
        } catch (Exception e) {
            LOG.info("\n ERROR IN REQUEST {} ", e.getMessage());
        }
        resp.getWriter().write("======FORM SUBMITTED======");
    }
}
