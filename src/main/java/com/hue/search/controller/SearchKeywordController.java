package com.hue.search.controller;

import com.hue.search.dto.SearchKeywordResponse;
import com.hue.search.service.SearchKeywordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SearchKeywordController {

    private final SearchKeywordService searchKeywordService;

    public SearchKeywordController(SearchKeywordService searchKeywordService) {
        this.searchKeywordService = searchKeywordService;
    }

    @GetMapping("/popular-keywords")
    public ResponseEntity<List<SearchKeywordResponse>> findPopularKeywords(){
        return ResponseEntity.ok(
                searchKeywordService.findPopularKeywords().stream()
                        .map(SearchKeywordResponse::from)
                        .collect(Collectors.toList())
        );
    }
}
