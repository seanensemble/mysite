/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.mysite.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mysite.core.services.TagService;
import com.mysite.core.workflows.SeanTagStep;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SeanTagServletModel {
    private static final Logger LOG = LoggerFactory.getLogger(SeanTagServletModel.class);

    @OSGiService
    TagService tagService;

    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE)
    @Default(values="No resourceType")
    protected String resourceType;

    @SlingObject
    private Resource currentResource;
    @SlingObject
    private ResourceResolver resourceResolver;

    @ValueMapValue
    private String[] svltags;


    @ValueMapValue
    private String texst;

    @PostConstruct
    protected void init() {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        String currentPagePath = Optional.ofNullable(pageManager)
            .map(pm -> pm.getContainingPage(currentResource))
            .map(Page::getPath).orElse("");
    }

    public String getTexst() {

        LOG.info("SeanTagServletModel_______getTexst_______SeanTagServletModel");

        return texst;
    }


    public String getSvltags() {
        LOG.info("getSvltags_1");

        List<String> tagList = new ArrayList<String>(){};
        String paramString = "";

        if(svltags != null) {
            for (int i = 0; i < svltags.length; i++) {
//                tagList.add(svltags[i]);
                paramString += String.format("&svltags=%s", svltags[i]);
            }

//            int taglength = svltags.length;
//            LOG.info("taglenght is : {}", taglength);

//            paramString.substring(1);
            paramString = StringUtils.replaceFirst(paramString, "&", "?");
        }

        return paramString;
    }

    public List<String> getNonCqProp() {

        List<String> tagtitles = tagService.getTagsTitle();
        return tagtitles;
    }
}
