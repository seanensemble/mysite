// /Users/seanl/VSABLE/_WORK_code/AEM/Maven/mysite/core/src/main/java/com/mysite/core/servlets/GeeksResourceTypesServlet.java

package com.mysite.core.servlets;

import com.day.cq.commons.jcr.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
        methods = {HttpConstants.METHOD_GET,HttpConstants.METHOD_POST},
        resourceTypes = "mysite/components/page", // from /apps/mysite/components/page
        selectors = {"geeks","test"}, //required selectors in URL
        extensions = {"xml"} //required extensions in URL
)
public class GeeksResourceTypesServlet extends SlingAllMethodsServlet {
    private static final Logger LOG = LoggerFactory.getLogger(GeeksResourceTypesServlet.class);

    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws ServletException, IOException {
        final Resource resource = req.getResource(); //whenever the resource is called, this servlet will be called. Here we get that resource from the request.
            // that resource contains all the jcr content.
        LOG.info("nnnnnnnnnnnoooooot_hiiiiiiiiittttttt");
        // Going to http://localhost:4502/content/mysite/us/en/servlet-page/jcr:content.geeks.txt?tag=tagone&tag=tagtwo

        String rpath = resource.getPath().toString();
        LOG.info("Resource path is: {}", rpath); // Resource path is: /content/mysite/us/en/servlet-page/jcr:content

        String[] tagParams = req.getParameterValues("tag");
        for(String tag: tagParams) {
            LOG.info("tag param looped is: {}", tag);
        }
        // tag param looped is: tagone
        // tag param looped is: tagtwo

        String pathInfo = req.getPathInfo();
        LOG.info("pathInfo__pathInfo__pathInfo");
        LOG.info(pathInfo); // /content/mysite/us/en/servlet-page/jcr:content.geeks.txt

        resp.setContentType("text/plain");
        resp.getWriter().write("Page Title = " + resource.getValueMap().get(JcrConstants.JCR_TITLE)); // Access using: http://localhost:4502/content/mysite/us/en/servlet-page/jcr:content.geeks.txt
    }
    @Override
    protected void doPost(SlingHttpServletRequest req, SlingHttpServletResponse resp)
            throws ServletException, IOException {
        try {
            LOG.info("\n ------------------------STARTED POST-------------------------");
            List<RequestParameter> requestParameterList=req.getRequestParameterList();
            for(RequestParameter requestParameter : requestParameterList){
                LOG.info("\n ==PARAMETERS===>  {} : {} ",requestParameter.getName(),requestParameter.getString());
            }
        }catch (Exception e){
            LOG.info("\n ERROR IN REQUEST {} ",e.getMessage());
        }
        resp.getWriter().write("======FORM SUBMITTED========");

    }

}
