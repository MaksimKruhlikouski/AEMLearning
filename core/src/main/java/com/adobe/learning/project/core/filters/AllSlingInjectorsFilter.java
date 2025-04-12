package com.adobe.learning.project.core.filters;

import java.io.IOException;
import javax.servlet.*;
import org.osgi.service.component.annotations.Component;

@Component(
        service = Filter.class,
        property = {
                "sling.filter.scope=REQUEST",
                "service.ranking:Integer=100"
        }
)
public class AllSlingInjectorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setAttribute("attr", "This attribute has been set via attribute");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
