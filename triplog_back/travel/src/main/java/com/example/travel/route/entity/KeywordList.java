package com.example.travel.route.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KeywordList {
    private Long key_id;
    private String keyword;

    @Builder
    public KeywordList(Long key_id, String keyword) {
        this.key_id = key_id;
        this.keyword = keyword;
    }
}