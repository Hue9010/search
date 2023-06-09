package com.hue.search.service;

import com.hue.search.model.SearchKeyword;
import com.hue.search.repository.SearchKeywordRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SearchKeywordService {

    private final SearchKeywordRepository searchKeywordRepository;

    public SearchKeywordService(SearchKeywordRepository searchKeywordRepository) {
        this.searchKeywordRepository = searchKeywordRepository;
    }

    @Cacheable(cacheNames = "keywords", key = "'top_10'")
    @Transactional(readOnly = true)
    public List<SearchKeyword> findPopularKeywords() {
        return searchKeywordRepository.findTop10ByOrderByCountDescUpdatedAtDesc();
    }

    @Transactional(readOnly = true)
    public SearchKeyword findSearchKeywordById(String keyword) {
        return searchKeywordRepository.findByKeyword(keyword)
                .orElseGet(() -> new SearchKeyword(keyword));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void hitKeyword(String searchKeyword) {
        searchKeywordRepository.findForUpdateByKeyword(searchKeyword)
                .ifPresentOrElse(
                        k -> k.hit(),
                        () -> searchKeywordRepository.save(new SearchKeyword(searchKeyword))
                );
    }

    @CacheEvict(cacheNames = "keywords", key = "'top_10'")
    public void cacheEvictHelper(){

    }
}
