package com.mysite.core.services;
import org.json.JSONObject;
import java.util.List;
public interface TagService {
//    public String getTagPaths(String tag, String path);
//    public JSONObject searchResult(String searchText,int startResult,int resultPerPage);
    public List<String> searchResult(String tagId, String searchPath);
}