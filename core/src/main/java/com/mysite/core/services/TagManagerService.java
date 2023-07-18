package com.mysite.core.services;
import java.util.List;

public interface TagManagerService {
    public void createTag(String tagPath, String tagTitle, String tagDescription);

    public void retrieveTags(String resourcePath);

    public List<String> retrieveCFbyTags(String cfPath, String[] tag);
}