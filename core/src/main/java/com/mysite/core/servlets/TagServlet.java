///Users/seanl/VSABLE/_WORK_code/AEM/Maven/mysite/core/src/main/java/com/mysite/core/servlets/TagServlet.java

package com.mysite.core.servlets;

import com.mysite.core.services.TagService;
import com.day.cq.commons.jcr.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
        methods = {HttpConstants.METHOD_GET,HttpConstants.METHOD_POST},
        resourceTypes = "mysite/components/page", // from /apps/mysite/components/page
        selectors = {"test"}, //required selectors in URL
        extensions = {"txt"} //required extensions in URL
)
//@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TagServlet extends SlingAllMethodsServlet {
    private static final Logger LOG = LoggerFactory.getLogger(TagServlet.class);

    @Reference
    TagService tagService;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws ServletException, IOException {
        final Resource resource = req.getResource(); //whenever the resource is called, this servlet will be called. Here we get that resource from the request.
        // that resource contains all the jcr content.
        LOG.info("------------------------TagServlet_doGet-------------------------");
        // Going to http://localhost:4502/content/mysite/us/en/servlet-page/jcr:content.test.txt?tag=tagone&tag=tagtwo

        String rpath = resource.getPath().toString();
        LOG.info("Resource path is: {}", rpath); // Resource path is: /content/mysite/us/en/servlet-page/jcr:content

        List<String> tagList=new ArrayList<String>();

        String[] tagParams = req.getParameterValues("svltags");

        String path = req.getParameter("path");

        for(String tag: tagParams) {
            LOG.info("tag111 param looped is: {}", tag);
            tagList.add(tag);
        }

        LOG.info("pathpathpathpathpathpath111 {}", path);

        // tag param looped is: tagone
        // tag param looped is: tagtwo

        String pathInfo = req.getPathInfo();
        LOG.info("pathInfo__pathInfo__pathInfo");
        LOG.info(pathInfo); // /content/mysite/us/en/servlet-page/jcr:content.geeks.txt

//        String searchPath = "/content/dam/mysite";

        LOG.info("tagService.getClass().toString(): {}");
        LOG.info(tagService.getClass().toString());

        List<String> serviceResult = tagService.searchResult(tagList, path);

        for (String result : serviceResult) {
            LOG.info("result________");
            LOG.info(result);
        }

//        resp.setContentType("text/plain");
//        resp.getWriter().write("Page Title = " + resource.getValueMap().get(JcrConstants.JCR_TITLE));
        resp.setContentType("application/json");

        resp.getWriter().write(serviceResult.toString());
    }

}



