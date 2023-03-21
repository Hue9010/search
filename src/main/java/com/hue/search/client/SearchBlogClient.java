package com.hue.search.client;

import com.hue.search.model.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "kakao", url = "https://dapi.kakao.com")
public interface SearchBlogClient {
    @GetMapping("/v2/search/blog")
    Optional<Page> searchBlog(@RequestParam("query") String query, @RequestParam("sort") String sort, @RequestParam("page") Integer page, @RequestParam("size") Integer size);
}
