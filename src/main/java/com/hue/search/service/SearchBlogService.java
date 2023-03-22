package com.hue.search.service;

import com.hue.search.client.SearchBlogClient;
import com.hue.search.dto.SearchKeywordEvent;
import com.hue.search.model.PageInterface;
import com.hue.search.model.Sort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class SearchBlogService {
    private final SearchBlogClient searchBlogClient;
    private final ApplicationEventPublisher eventPublisher;


    public SearchBlogService(SearchBlogClient searchBlogClient, ApplicationEventPublisher eventPublisher) {
        this.searchBlogClient = searchBlogClient;
        this.eventPublisher = eventPublisher;
    }

    public PageInterface searchBlog(String query, Sort sort, Integer page, Integer size) {
        eventPublisher.publishEvent(new SearchKeywordEvent(query));
        return searchBlogClient.searchBlog(query, sort, page, size);
    }
}
