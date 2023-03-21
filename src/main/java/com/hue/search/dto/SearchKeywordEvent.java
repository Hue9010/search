package com.hue.search.dto;

import lombok.Getter;

public class SearchKeywordEvent {
    @Getter
    private String keyword;

    public SearchKeywordEvent(String searchKeyword) {
        this.keyword = searchKeyword;
    }
}
