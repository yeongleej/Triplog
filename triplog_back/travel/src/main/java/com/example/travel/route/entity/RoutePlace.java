package com.example.travel.route.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class RoutePlace {

    private Long place_id;

    private final Long spot_id;

    private final Long route_id;

    private final String rp_name;

    private int priority;

    private final LocalDateTime visit_time;


    @Builder
    public RoutePlace(Long spot_id, Long route_id, String rp_name, int priority, LocalDateTime visit_time) {
        this.spot_id = spot_id;
        this.route_id = route_id;
        this.rp_name = rp_name;
        this.priority = priority;
        this.visit_time = visit_time;
    }


    @Builder
    public RoutePlace(Long place_id, Long spot_id, Long route_id, String rp_name, int priority, LocalDateTime visit_time) {
        this.place_id = place_id;
        this.spot_id = spot_id;
        this.route_id = route_id;
        this.rp_name = rp_name;
        this.priority = priority;
        this.visit_time = visit_time;
    }

    public void changePriority(int priority) {
        this.priority = priority;
    }
}