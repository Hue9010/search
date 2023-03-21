package com.hue.search.dto;

import com.hue.search.model.SearchKeyword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SearchKeywordResponse {
    private String keyword;

    private Integer count;

    public static SearchKeywordResponse from(SearchKeyword searchKeyword) {
        return SearchKeywordResponse.builder()
                .keyword(searchKeyword.getKeyword())
                .count(searchKeyword.getCount())
                .build();
    }
}
