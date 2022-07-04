// /Users/seanl/VSABLE/_WORK_code/AEM/Maven/mysite/core/src/main/java/com/mysite/core/services/impl/TagServiceImpl.java

package com.mysite.core.services.impl;

import com.mysite.core.services.TagService;
import com.mysite.core.utils.ResolverUtil;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@Component(service = TagService.class,immediate = true)
public class TagServiceImpl implements TagService {
    private static final Logger LOG= LoggerFactory.getLogger(TagService.class);

    @Reference
    QueryBuilder queryBuilder;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Activate
    public void activate(ComponentContext componentContext){
        LOG.info("\n ==============INSIDE1 ACTIVATE================");
        System.out.println("\n ==============INSIDE1 ACTIVATE================");
    }

    @Deactivate
    public void deactivate(ComponentContext componentContext){
        LOG.info("\n ==============INSIDE1 DEACTIVATE================");
        System.out.println("\n ==============INSIDE1 DEACTIVATE================");
    }

    @Modified
    public void modified(ComponentContext componentContext){
        LOG.info("\n ==============INSIDE1 MODIFIED================");
        System.out.println("\n ==============INSIDE1 MODIFIED================");
    }

//    @Override
//    public String getTagPaths(String tag, String path) {
//        System.out.println("");
//        System.out.println("__________printed from getTagPaths___________");
//        System.out.println(String.format("tag: %s and path: %s", tag, path));
//        System.out.println("");
//
//        System.out.println(" JSON OBJECT from searchResult");
//        System.out.println(this.searchResult("women", 5,10));
//
//        return String.format("tag: %s and path: %s", tag, path);
//    }


    public Map<String,String> createTextSearchQuery(List<String> tagId, String searchPath){
        Map<String,String> queryMap=new HashMap<>();
//        queryMap.put("type","cq:Asset");
        queryMap.put("type","dam:Asset");
        queryMap.put("path","/content/dam/mysite");
        queryMap.put("property.and","true");
        queryMap.put("property", "jcr:content/metadata/cq:tags");

//        queryMap.put("property.1_value","seantags:tagone");
//        queryMap.put("property.2_value","%tagtwo%");

//        queryMap.put("path", searchPath);
//        queryMap.put("tagid", tagId);

        System.out.println("");
        System.out.println("");

        System.out.println("tagId.size()");
        System.out.println(tagId.size());

        for (int i = 1; i <= tagId.size(); i++) {
            System.out.println("new loooooooop");
            System.out.println(i);
            System.out.println(tagId.get(i-1)); // Cannot access the iterator
            System.out.println("above for value and i");
            String prop = String.format("property.%s_value", String.valueOf(i));
            String tagInclude = String.format("%%%s%%", tagId.get(i-1));
            System.out.println("prop...............");
            System.out.println(prop);
            System.out.println("tagInclude...............");
            System.out.println(tagInclude);
            queryMap.put(prop,tagInclude);
        }

        System.out.println("");
        System.out.println("");

        queryMap.put("property.operation","like");

        return queryMap;
    }

    @Override
    public List<String> searchResult(List<String> tagId, String searchPath){
        System.out.println("\n ----SEARCH RESULT--------searchResult");
        JSONObject searchResult=new JSONObject();
        List<Hit> hits = new ArrayList();
        List<String> paths = new ArrayList();
        ResourceResolver resourceResolver = null;
        try {
            System.out.println("\n tryyyyyyyy");

            resourceResolver = ResolverUtil.newResolver(resourceResolverFactory);
            final Session session = resourceResolver.adaptTo(Session.class);
            Query query = queryBuilder.createQuery(PredicateGroup.create(createTextSearchQuery(tagId,searchPath)), session);

            SearchResult rawResults = query.getResult();
            Iterator<Node> nodeList = rawResults.getNodes();

            hits = query.getResult().getHits();
            System.out.println("aaaa hitsize");
            System.out.println(hits.size());

            for (Hit temp: hits) {
                System.out.println("");
                System.out.println("--------temp.getPath()");
                System.out.println(temp.getPath());
                System.out.println("");
                paths.add(temp.getPath());
//                temp.getNode().getProperty("jcr:title");
            }

        }catch (Exception e){
            System.out.println("\n ----ERROR -----{} ");
            System.out.println(e.getMessage());
        }
        finally {
            if(resourceResolver != null) {
                resourceResolver.close();
            }
        }
//        return searchResult;
        return paths;
    }

    public List<String> getTagsTitle() {
        List<Hit> tagHits = new ArrayList();
        List<String> tagList = new ArrayList();

        Map<String,String> tagQueryMap=new HashMap<>();
        tagQueryMap.put("type","cq:tags");
        tagQueryMap.put("path","/content/cq:tags/seantags");

        try {
            ResourceResolver resourceResolver = ResolverUtil.newResolver(resourceResolverFactory);
            final Session session = resourceResolver.adaptTo(Session.class);

            Query query = queryBuilder.createQuery(PredicateGroup.create(tagQueryMap), session);

            tagHits = query.getResult().getHits();

            for (Hit temp: tagHits) {
                System.out.println("");
                System.out.println("aaaaaa_______tagHits__title");
                String title = temp.getNode().getProperty("jcr:title").getString();
                System.out.println(title);
                System.out.println("");
                tagList.add(title);
            }

        } catch (Exception e){
            System.out.println("\n ----ERROR -----{} ");
            System.out.println(e.getMessage());
        };

        return tagList;

    };

}