package com.example.travel.route.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Cities {
    private Long city_id;
    private String city_name;

    @Builder
    public Cities(Long city_id, String city_name) {
        this.city_id = city_id;
        this.city_name = city_name;
    }

    @Builder
    public Cities(String city_name) {
        this.city_name = city_name;
    }
}