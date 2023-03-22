package com.hue.search.client;

import com.hue.search.model.KakaoPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "kakao", url = "https://dapi.kakao.com")
public interface SearchKakaoBlogClient {
    @GetMapping("/v2/search/blog")
    Optional<KakaoPage> searchBlog(@RequestParam("query") String query, @RequestParam("sort") String sort, @RequestParam("page") Integer page, @RequestParam("size") Integer size);
}
