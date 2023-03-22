package com.hue.search.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hue.search.dto.SearchResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
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
                                .createdAt(
                                        LocalDate.parse(i.getPostdate(), DateTimeFormatter.ofPattern("yyyyMMdd"))
                                                .atStartOfDay()
                                )
                                .build()
                        ).collect(Collectors.toList()))
                .build();
    }

    @Getter
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
