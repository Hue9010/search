package com.hue.search.service;

import com.hue.search.model.SearchKeyword;
import com.hue.search.repository.SearchKeywordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SearchKeywordService {

    private final SearchKeywordRepository searchKeywordRepository;

    public SearchKeywordService(SearchKeywordRepository searchKeywordRepository) {
        this.searchKeywordRepository = searchKeywordRepository;
    }

    @Transactional(readOnly = true)
    public List<SearchKeyword> findPopularKeywords() {
        return searchKeywordRepository.findTop10ByOrderByCountDescUpdatedAtDesc();
    }

    @Transactional
    public void hitKeyword(String searchKeyword) {
        searchKeywordRepository.findByKeyword(searchKeyword)
                .ifPresentOrElse(
                        k -> k.hit(),
                        () -> searchKeywordRepository.save(new SearchKeyword(searchKeyword))
                );
    }
}
