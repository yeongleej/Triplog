package com.example.travel.route.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ReviewImage {

    private Long rv_id;
    private Long review_id;
    private Long img_id;
    private String img_url;

    public ReviewImage() {}

    @Builder
    public ReviewImage(Long review_id, Long img_id, String img_url) {
        this.review_id = review_id;
        this.img_id = img_id;
        this.img_url = img_url;
    }

    @Builder
    public ReviewImage (Long rv_id, Long review_id, Long img_id, String img_url) {
        this.rv_id = rv_id;
        this.review_id = review_id;
        this.img_id = img_id;
        this.img_url = img_url;
    }
}