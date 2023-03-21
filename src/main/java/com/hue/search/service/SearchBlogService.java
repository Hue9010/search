package com.hue.search.service;

import com.hue.search.client.SearchBlogClient;
import com.hue.search.dto.Sort;
import com.hue.search.model.Page;
import org.springframework.stereotype.Service;

@Service
public class SearchBlogService {
    private final SearchBlogClient searchBlogClient;

    public SearchBlogService(SearchBlogClient searchBlogClient) {
        this.searchBlogClient = searchBlogClient;
    }

    public Page searchBlog(String query, Sort sort, Integer page, Integer size) {
        return searchBlogClient.searchBlog(query, sort.getValue(), page, size).get();
    }
}
