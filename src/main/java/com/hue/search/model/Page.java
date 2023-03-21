package com.hue.search.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

public class Page {
    @Getter
    private Meta meta;

    @Getter
    private List<Document> documents;

    @Data
    @ToString
    @NoArgsConstructor
    public static class Meta {
        @JsonProperty("total_count")
        private Integer totalCount;
        @JsonProperty("pageable_count")
        private Integer pageableCount;
        @JsonProperty("is_end")
        private Boolean isEnd;
    }

    @Getter
    @ToString
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
