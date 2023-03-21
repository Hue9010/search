package com.hue.search.service;

import com.hue.search.dto.SearchKeywordEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {
    private final SearchKeywordService searchKeywordService;

    public EventService(SearchKeywordService searchKeywordService) {
        this.searchKeywordService = searchKeywordService;
    }

    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void hitSearchKeyword(SearchKeywordEvent event){
        searchKeywordService.hitKeyword(event.getKeyword());
    }
}
