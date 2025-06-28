package com.adobe.learning.project.core.servlets;

import com.day.cq.commons.jcr.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.List;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
        methods = {HttpConstants.METHOD_GET, HttpConstants.METHOD_POST},
        resourceTypes = "learning-project/components/page",
        selectors = {"mobile", "tablet"},
        extensions = {"txt", "xml"}
)
public class SimpleServletResourceType extends SlingAllMethodsServlet {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleServletResourceType.class);

    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws IOException {
        final Resource resource = req.getResource();
        resp.setContentType("text/plain");
        resp.getWriter().write("Page Title = " + resource.getValueMap().get(JcrConstants.JCR_TITLE));
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
