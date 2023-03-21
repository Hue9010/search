package com.hue.search.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SearchKeywordTest {

    @Test
    void hitTest() {
        // given
        var keyword = new SearchKeyword("some");

        // when
        keyword.hit();

        // then
        assertThat(keyword.getCount()).isEqualTo(SearchKeyword.INIT_COUNT + 1);
    }
}
