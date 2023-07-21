package com.mysite.core.services;
import org.osgi.annotation.versioning.ProviderType;

import java.util.List;

@ProviderType
public interface TagManagerService {
    public void createGivenTag(String tagPath, String tagTitle, String tagDescription);

    public void retrieveTags(String resourcePath);

    public List<String> retrieveCFbyTags(String cfPath, String[] tag);
}