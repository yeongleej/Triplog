package com.example.travel.route.dto.response;

import com.example.travel.route.entity.ReviewImage;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReviewResponseDto {

    private Long review_id;
    private final Long place_id;
    private final String review_text;
    private final List<ReviewImage> imageList;

    @Builder
    public ReviewResponseDto(Long review_id, Long place_id, String review_text, List<ReviewImage> imageList) {
        this.review_id = review_id;
        this.place_id = place_id;
        this.review_text = review_text;
        this.imageList = imageList;
    }

    @Builder
    public ReviewResponseDto(Long place_id, String review_text, List<ReviewImage> imageList) {
        this.place_id = place_id;
        this.review_text = review_text;
        this.imageList = imageList;
    }
}