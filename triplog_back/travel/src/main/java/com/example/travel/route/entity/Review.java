package com.example.travel.route.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class Review {
    private Long review_id;
    private Long place_id;
    private String review_text;

    public Review() {
    }

    @Builder
    public Review(Long review_id, Long place_id, String review_text) {
        this.review_id = review_id;
        this.place_id = place_id;
        this.review_text = review_text;
    }

    @Builder
    public Review(Long review_id, Long place_id) {
        this.review_id = review_id;
        this.place_id = place_id;
    }

    @Builder
    public Review(String review_text) {
        this.review_text = review_text;
    }
}