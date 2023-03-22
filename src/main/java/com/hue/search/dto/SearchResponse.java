package com.hue.search.dto;

import com.hue.search.model.KakaoPage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SearchResponse {
    private MetaResponse meta;

    private List<DocumentResponse> documents;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MetaResponse {
        private Integer totalCount;
        private Integer pageableCount;
        private Boolean isEnd;

        public static MetaResponse fromMeta(KakaoPage.Meta meta) {
            return MetaResponse.builder()
                    .totalCount(meta.getTotalCount())
                    .pageableCount(meta.getPageableCount())
                    .isEnd(meta.getIsEnd())
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class DocumentResponse {
        private String title;
        private String contents;
        private String url;
        private String blogName;
        private String thumbnail;
        private String createdAt;
    }
}
