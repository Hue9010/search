package com.hue.search.service;

import com.hue.search.dto.SearchKeywordEvent;
import com.hue.search.model.SearchKeyword;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService {
    private final SearchKeywordService searchKeywordService;

    public EventService(SearchKeywordService searchKeywordService) {
        this.searchKeywordService = searchKeywordService;
    }

    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void hitSearchKeyword(SearchKeywordEvent event) {
        searchKeywordService.hitKeyword(event.getKeyword());

        List<SearchKeyword> searchKeywords = searchKeywordService.findPopularKeywords();
        if (searchKeywords.size() < SearchKeyword.POPULAR_KEYWORD_MAX_COUNT) {
            searchKeywordService.cacheEvictHelper();
        }

        SearchKeyword searchKeyword = searchKeywordService.findSearchKeywordById(event.getKeyword());
        if (searchKeywords.stream()
                .anyMatch(keyword -> keyword.getCount() <= searchKeyword.getCount())) {
            searchKeywordService.cacheEvictHelper();
        }
    }
}
