///Users/seanl/VSABLE/_WORK_code/AEM/Maven/mysite/core/src/main/java/com/mysite/core/servlets/TagServletPath.java

package com.mysite.core.servlets;

import com.mysite.core.services.TagManagerService;
import com.mysite.core.services.TagService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
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
@SlingServletPaths(
//        value = {"/bin/pages","/geeks/pages"}
    value = {"/random/TagManagerServlet/page"}
)
public class TagManagerServlet extends SlingAllMethodsServlet {
    private static final Logger LOG = LoggerFactory.getLogger(TagManagerServlet.class);

    @Reference
    TagManagerService tagManagerService;

    @Override
    protected void doPost(SlingHttpServletRequest req, SlingHttpServletResponse resp)
            throws ServletException, IOException {

        LOG.info("TagManagerServlet calledddddd");
        LOG.error("TagManagerServlet calledddddd");

//        try {
//            tagManagerService.createTag("/content/cq:tags/customtags", "tagCustom2", "tag custom desc");
//        }
//        catch (Exception e) {
//            LOG.info("\n ERROR IN REQUEST {} ",e.getMessage());
//        }

        resp.setContentType("application/json");

//        resp.getWriter().write(serviceResult.toString());
        resp.getWriter().write("TagManagerServlet");

    }
}
