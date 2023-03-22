package com.hue.search.client;

import com.hue.search.model.NaverPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "naver", url = "https://openapi.naver.com")
public interface SearchNaverBlogClient {
    @GetMapping("/v1/search/blog.json")
    Optional<NaverPage> searchBlog(@RequestParam("query") String query, @RequestParam("sort") String sort, @RequestParam("start") Integer start, @RequestParam("display") Integer display);
}
