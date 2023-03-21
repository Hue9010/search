package com.hue.search.service;

import com.hue.search.model.SearchKeyword;
import com.hue.search.repository.SearchKeywordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchKeywordServiceTest {
    private static String TEST_KEYWORD = "test";

    @Mock
    private SearchKeywordRepository searchKeywordRepository;
    @InjectMocks
    private SearchKeywordService searchKeywordService;

    @Test
    @DisplayName("새로운 키워드 hit시 초기 카운트 값 유지")
    void hit_new_keyword() {
        // given
        SearchKeyword newKeyword = new SearchKeyword(TEST_KEYWORD);
        when(searchKeywordRepository.findByKeyword(TEST_KEYWORD)).thenReturn(Optional.empty());
        when(searchKeywordRepository.save(any(SearchKeyword.class))).thenReturn(newKeyword);

        // when
        searchKeywordService.hitKeyword(TEST_KEYWORD);

        // then
        verify(searchKeywordRepository, times(1)).save(any(SearchKeyword.class));
        assertThat(newKeyword.getCount()).isEqualTo(SearchKeyword.INIT_COUNT);
    }

    @Test
    @DisplayName("존재하고 있는 키워드 hit시 카운트 값 증가")
    void hit_exist_keyword() {
        // given
        SearchKeyword existKeyword = new SearchKeyword(TEST_KEYWORD);
        int originCount = existKeyword.getCount();
        when(searchKeywordRepository.findByKeyword(TEST_KEYWORD)).thenReturn(Optional.of(existKeyword));

        // when
        searchKeywordService.hitKeyword(TEST_KEYWORD);

        // then
        verify(searchKeywordRepository, times(0)).save(existKeyword);
        assertThat(existKeyword.getCount()).isEqualTo(originCount + 1);
    }
}
