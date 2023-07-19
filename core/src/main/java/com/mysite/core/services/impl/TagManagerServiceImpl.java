package com.mysite.core.services.impl;

import com.mysite.core.helper.Helper1;
import com.mysite.core.services.TagManagerService;
import org.osgi.service.component.annotations.Component;

import javax.inject.Inject;

@Component(service = TagManagerService.class, immediate = true)
public class TagManagerServiceImpl implements TagManagerService {

    @Inject
    private Helper1 helper;

    public void createTag(String tagPath, String tagTitle, String tagDescription) {
        helper.getBaeldungString();
    }
}
