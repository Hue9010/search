package com.hue.search.client;

import com.hue.search.exception.ClientNotFoundException;
import com.hue.search.exception.ClientServerException;
import com.hue.search.model.KakaoPage;
import com.hue.search.model.NaverPage;
import com.hue.search.model.PageInterface;
import com.hue.search.model.Sort;
import org.springframework.stereotype.Service;

@Service
public class SearchBlogClient {
    private final SearchKakaoBlogClient searchKakaoBlogClient;
    private final SearchNaverBlogClient searchNaverBlogClient;

    public SearchBlogClient(SearchKakaoBlogClient searchKakaoBlogClient, SearchNaverBlogClient searchNaverBlogClient) {
        this.searchKakaoBlogClient = searchKakaoBlogClient;
        this.searchNaverBlogClient = searchNaverBlogClient;
    }

    public PageInterface searchBlog(String query, Sort sort, Integer page, Integer size) {
        try {
            return searchKakaoBlogClient.searchBlog(query, sort.getKakao(), page, size).orElseGet(KakaoPage::emptyPage);
        } catch (ClientServerException | ClientNotFoundException e) {
            return this.searchNaverBlog(query, sort, page, size);
        }
    }

    private PageInterface searchNaverBlog(String query, Sort sort, Integer page, Integer size) {
        return searchNaverBlogClient.searchBlog(query, sort.getNaver(), cursorPageToOffsetStart(page, size), size)
                .orElseGet(NaverPage::emptyPage);
    }

    public static int cursorPageToOffsetStart(int page, int size) {
        return (size * (page - 1)) + 1;
    }

}
