package com.hue.search.model;

import lombok.Getter;

@Getter
public enum Sort {
    ACCURACY("accuracy", "sim"),
    RECENCY("recency", "date");

    private String kakao;
    private String naver;

    Sort(String kakao, String naver) {
        this.kakao = kakao;
        this.naver = naver;
    }
}
