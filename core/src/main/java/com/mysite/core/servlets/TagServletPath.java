///Users/seanl/VSABLE/_WORK_code/AEM/Maven/mysite/core/src/main/java/com/mysite/core/servlets/TagServletPath.java

package com.mysite.core.servlets;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mysite.core.services.TagService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Component(service = Servlet.class)
@SlingServletPaths(
//        value = {"/bin/pages","/geeks/pages"}
        value = {"/random/nonexisiting/page"}
)
public class TagServletPath extends SlingAllMethodsServlet {
    private static final Logger LOG = LoggerFactory.getLogger(TagServletPath.class);

    @Reference
    TagService tagService;


    @Override
    protected void doPost(SlingHttpServletRequest req, SlingHttpServletResponse resp)
            throws ServletException, IOException {

        List<String> serviceResult = new ArrayList<String>(){};
        List<String> inputTags = new ArrayList<String>(){};

        String searchpath = "/notexisting";

        try {
            LOG.info("------------------------STARTEDGeeksPathTypeServletPOST-------------------------");
            List<RequestParameter> requestParameterList=req.getRequestParameterList();
            for(RequestParameter requestParameter : requestParameterList){
                LOG.info("\n ==PARAMETERS===>  {} : {} ",requestParameter.getName(),requestParameter.getString());

                if(requestParameter.getName().contains("tagbox")) {
                    inputTags.add(requestParameter.getString());
                }

                if(requestParameter.getName().equals("spath")) {
                    searchpath = requestParameter.getString();
                    LOG.info("spath___TagServletPath {}", searchpath);
                }
            }

            LOG.info("what_is_the_size_of_inputTags? : {}", inputTags.size());
            for (String result : inputTags) {
                LOG.info("11111____result______inputTags_____result_____");
                LOG.info(result);
            };

//            String searchPath = "/content/dam/mysite";

            serviceResult = tagService.searchResult(inputTags, searchpath);

            for (String result : serviceResult) {
                LOG.info("result________TagServletPath");
                LOG.info(result);
            };

        }catch (Exception e){
            LOG.info("\n ERROR IN REQUEST {} ",e.getMessage());
        }
        resp.setContentType("application/json");

        resp.getWriter().write(serviceResult.toString());

    }
}
