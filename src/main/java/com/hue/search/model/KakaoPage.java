package com.hue.search.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hue.search.dto.SearchResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
public class KakaoPage implements PageInterface {
    @Getter
    private Meta meta;

    @Getter
    private List<Document> documents;

    public static KakaoPage emptyPage() {
        return KakaoPage.builder()
                .meta(Meta.emptyMeta())
                .documents(Collections.emptyList())
                .build();
    }

    @Override
    public SearchResponse toSearchResponse() {
        return SearchResponse.builder()
                .meta(SearchResponse.MetaResponse.builder()
                        .totalCount(this.meta.getTotalCount())
                        .pageableCount(this.meta.getPageableCount())
                        .isEnd(this.meta.getIsEnd())
                        .build())
                .documents(
                        this.documents.stream()
                                .map(document -> SearchResponse.DocumentResponse.builder()
                                        .title(document.getTitle())
                                        .contents(document.getContents())
                                        .url(document.getUrl())
                                        .blogName(document.getBlogName())
                                        .thumbnail(document.getThumbnail())
                                        .createdAt(
                                                LocalDateTime.parse(document.getDatetime(), DateTimeFormatter.ISO_DATE_TIME)
                                        )
                                        .build()
                                )
                                .collect(Collectors.toList()))
                .build();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Meta {
        @JsonProperty("total_count")
        private Integer totalCount;
        @JsonProperty("pageable_count")
        private Integer pageableCount;
        @JsonProperty("is_end")
        private Boolean isEnd;

        public static Meta emptyMeta() {
            return Meta.builder()
                    .totalCount(0)
                    .pageableCount(0)
                    .isEnd(true)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Document {
        private String title;
        private String contents;
        private String url;
        private String blogName;
        private String thumbnail;
        private String datetime;
    }
}
