package com.hue.search.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hue.search.dto.SearchResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NaverPage implements PageInterface {
    private Integer total;
    private Integer start;
    private Integer display;

    private List<Item> items;

    public static NaverPage emptyPage() {
        return NaverPage.builder()
                .total(0)
                .start(1)
                .display(0)
                .items(Collections.emptyList())
                .build()
                ;
    }

    @Override
    public SearchResponse toSearchResponse() {
        return SearchResponse.builder()
                .meta(SearchResponse.MetaResponse.builder()
                        .totalCount(this.total)
                        .pageableCount(this.total)
                        .isEnd(this.start + this.display > total)
                        .build())
                .documents(this.items.stream()
                        .map(i -> SearchResponse.DocumentResponse.builder()
                                .title(i.title)
                                .contents(i.getDescription())
                                .url(i.link)
                                .blogName(i.bloggerName)
                                .createdAt(i.postdate)
                                .build()
                        ).collect(Collectors.toList()))
                .build();
    }

    @Data
    @NoArgsConstructor
    public static class Item {
        private String title;
        private String link;
        private String description;
        @JsonProperty("bloggername")
        private String bloggerName;
        @JsonProperty("bloggerlink")
        private String bloggerLink;
        private String postdate;
    }
}
