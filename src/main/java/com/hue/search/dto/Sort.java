package com.hue.search.dto;

import lombok.Getter;

@Getter
public enum Sort {
    ACCURACY("accuracy"),
    RECENCY("recency");

    private String value;

    Sort(String value) {
        this.value = value;
    }
}
