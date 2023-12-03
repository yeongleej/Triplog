package com.example.travel.route.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RouteImage {
    private Long rt_id;
    private Long route_id;
    private Long img_id;
    private String img_url;

    public RouteImage() {
    }

    @Builder
    public RouteImage(Long route_id, Long img_id, String img_url) {
        this.route_id = route_id;
        this.img_id = img_id;
        this.img_url = img_url;
    }
}
