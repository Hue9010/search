package com.hue.search.client;

import com.hue.search.exception.ClientErrorException;
import com.hue.search.exception.ClientNotFoundException;
import com.hue.search.exception.ClientServerException;
import com.hue.search.model.Sort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static com.hue.search.client.SearchBlogClient.cursorPageToOffsetStart;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchBlogClientTest {

    @Mock
    private SearchKakaoBlogClient searchKakaoBlogClient;

    @Mock
    private SearchNaverBlogClient searchNaverBlogClient;

    @InjectMocks
    private SearchBlogClient searchBlogClient;

    @DisplayName("카카오 블로그 검색 API 장애시 네이버 블로그 검색 API로 호출 (404는 일시적 도메인 오류 가능성이 있어 재시도)")
    @ParameterizedTest
    @MethodSource("naverRetryExceptions")
    void retryNaverClientTest(RuntimeException exception) {
        // given
        String query = "test";
        Sort sort = Sort.ACCURACY;
        Integer page = 1;
        Integer size = 10;
        when(searchKakaoBlogClient.searchBlog(query, sort.getKakao(), page, size)).thenThrow(exception.getClass());

        // when
        searchBlogClient.searchBlog(query, sort, page, size);

        // then
        verify(searchKakaoBlogClient, times(1)).searchBlog(query, sort.getKakao(), page, size);
        verify(searchNaverBlogClient, times(1)).searchBlog(query, sort.getNaver(), cursorPageToOffsetStart(page, size), size);
    }

    static Stream<Arguments> naverRetryExceptions() {
        return Stream.of(
                Arguments.arguments(new ClientNotFoundException()),
                Arguments.arguments(new ClientServerException())
        );

    }

    @DisplayName("카카오 블로그 API 호출시 잘못된 요청으로 인한 4xx 응답시 최종 실패")
    @Test
    void noRetryOtherClient() {
        // given
        String query = "test";
        Sort sort = Sort.ACCURACY;
        Integer page = 1;
        Integer size = 10;
        when(searchKakaoBlogClient.searchBlog(query, sort.getKakao(), page, size)).thenThrow(ClientErrorException.class);

        try {
            // when
            searchBlogClient.searchBlog(query, sort, page, size);

            // then
            Assertions.fail();
        } catch (ClientErrorException e) {
            verify(searchKakaoBlogClient, times(1)).searchBlog(query, sort.getKakao(), page, size);
            verify(searchNaverBlogClient, times(0)).searchBlog(query, sort.getNaver(), cursorPageToOffsetStart(page, size), size);
        }
    }
}
