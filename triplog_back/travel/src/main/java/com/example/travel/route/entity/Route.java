package com.example.travel.route.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@ToString
public class Route {

    private Long route_id;

    private Long uid;

    private Long city_id;

    private Long key_id;

    private String route_name;

    private LocalDate route_day;

    private MultipartFile route_img;

    public Route() {
    }

    @Builder
    public Route(Long uid, Long city_id, Long key_id, String route_name, LocalDate route_day) {
        this.uid = uid;
        this.city_id = city_id;
        this.key_id = key_id;
        this.route_name = route_name;
        this.route_day = route_day;
    }

    @Builder
    public Route(Long route_id, Long uid, Long city_id, Long key_id, String route_name, LocalDate route_day) {
        this.route_id = route_id;
        this.uid = uid;
        this.city_id = city_id;
        this.key_id = key_id;
        this.route_name = route_name;
        this.route_day = route_day;
    }

    @Builder
    public Route(Long uid, Long city_id, Long key_id, String route_name, LocalDate route_day, MultipartFile route_img) {
        this.uid = uid;
        this.city_id = city_id;
        this.key_id = key_id;
        this.route_name = route_name;
        this.route_day = route_day;
        this.route_img = route_img;
    }
}