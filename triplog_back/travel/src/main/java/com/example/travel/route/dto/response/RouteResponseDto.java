package com.example.travel.route.dto.response;

import com.example.travel.route.entity.Route;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RouteResponseDto {

    private final Long route_id;
    private final String city_name;
    private final String keyword;
    private final String route_name;
    private final LocalDate route_day;
    private String img_url;

//    public RouteResponseDto(Route route){
//        this.route_id = route.getRoute_id();
//        this.city_id = route.getCity_id();
//        this.key_id = route.getKey_id();
//        this.route_name = route.getRoute_name();
//        this.route_day = route.getRoute_day();
//    }

    @Builder
    public RouteResponseDto(Long route_id, String city_name, String keyword, String route_name, LocalDate route_day, String img_url) {
        this.route_id = route_id;
        this.city_name = city_name;
        this.keyword = keyword;
        this.route_name = route_name;
        this.route_day = route_day;
        this.img_url = img_url;
    }
}