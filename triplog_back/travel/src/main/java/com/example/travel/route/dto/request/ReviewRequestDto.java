package com.example.travel.route.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@ToString
public class ReviewRequestDto {

    private final Long place_id;
    private final List<MultipartFile> review_imgs;
    private final String review_text;

    @Builder
    public ReviewRequestDto(Long place_id, List<MultipartFile> review_imgs, String review_text) {
        this.place_id = place_id;
        this.review_imgs = review_imgs;
        this.review_text = review_text;
    }
}
