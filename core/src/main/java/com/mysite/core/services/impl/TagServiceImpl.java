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
        queryMap.put("type","cq:Asset");
        queryMap.put("path","/content/dam/mysite");
        queryMap.put("property.and","true");
        queryMap.put("property", "jcr:content/metadata/cq:tags");

        queryMap.put("property.1_value","seantags:tagone");
        queryMap.put("property.2_value","tagtwo");
//        queryMap.put("path", searchPath);
//        queryMap.put("tagid", tagId);
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

            hits = query.getResult().getHits();
            System.out.println("aaaa hitsize");
            System.out.println(hits.size());

            for (Hit temp: hits) {
                System.out.println("");
                System.out.println("--------temp.getPath()");
                System.out.println(temp.getPath());
                System.out.println("");
                paths.add(temp.getPath());
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

}