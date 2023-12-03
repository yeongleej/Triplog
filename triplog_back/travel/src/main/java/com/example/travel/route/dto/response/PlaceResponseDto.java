package com.example.travel.route.dto.response;

import com.example.travel.route.entity.ReviewImage;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
public class PlaceResponseDto implements Serializable{

    private final Long place_id;
    private final String placeName;
    private final String address;
    private final String routeName;
    private final int priority;
    private final LocalDateTime visitTime;
    private Long review_id;
    private String review_text;
    private List<ReviewImage> imageList;
    private Double longitude;
    private Double latitude;

    @Builder
    public PlaceResponseDto(Long place_id, String placeName, String address, String routeName, int priority,
                            LocalDateTime visitTime, Long review_id, String review_text, List<ReviewImage> imageList,
                            Double longitude, Double latitude) {
        this.place_id = place_id;
        this.placeName = placeName;
        this.address = address;
        this.routeName = routeName;
        this.priority = priority;
        this.visitTime = visitTime;
        this.review_id = review_id;
        this.review_text = review_text;
        this.imageList = imageList;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}