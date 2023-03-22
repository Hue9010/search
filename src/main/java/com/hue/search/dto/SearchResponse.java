package com.hue.search.dto;

import com.hue.search.model.KakaoPage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Data
@Builder
@AllArgsConstructor
public class SearchResponse {
    private MetaResponse meta;

    private List<DocumentResponse> documents;

    public static SearchResponse fromPage(KakaoPage kakaoPage) {
        return SearchResponse.builder()
                .meta(MetaResponse.fromMeta(kakaoPage.getMeta()))
                .documents(kakaoPage.getDocuments().stream()
                        .map(DocumentResponse::fromDocument)
                        .collect(Collectors.toList()))
                .build();
    }

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

        public static DocumentResponse fromDocument(KakaoPage.Document document) {
            return DocumentResponse.builder()
                    .title(document.getTitle())
                    .contents(document.getContents())
                    .url(document.getUrl())
                    .blogName(document.getBlogName())
                    .thumbnail(document.getThumbnail())
                    .createdAt(document.getDatetime())
                    .build();
        }
    }
}
