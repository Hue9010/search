package com.hue.search.controller;

import com.hue.search.dto.SearchResponse;
import com.hue.search.dto.Sort;
import com.hue.search.service.SearchBlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@Validated
public class SearchBlogController {

    private final SearchBlogService searchBlogService;

    public SearchBlogController(SearchBlogService searchBlogService) {
        this.searchBlogService = searchBlogService;
    }

    @GetMapping("/search/blog")
    public ResponseEntity<SearchResponse> searchBlogs(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "ACCURACY") Sort sort,
            @RequestParam(required = false, defaultValue = "1") @Min(1) @Max(50) Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(50) Integer size) {
        return ResponseEntity.ok(
                SearchResponse.fromPage(searchBlogService.searchBlog(query, sort, page, size))
        );
    }
}
