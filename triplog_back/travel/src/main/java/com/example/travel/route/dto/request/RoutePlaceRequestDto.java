package com.example.travel.route.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RoutePlaceRequestDto {

    private final Long spot_id;
    private final Long route_id;
    private final String rp_name;
    private final int priority;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private final LocalDateTime visit_time;

    @Builder
    public RoutePlaceRequestDto(Long spotId, Long routeId, String rpName, int priority,
                                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
                                LocalDateTime visitTime) {
        this.spot_id = spotId;
        this.route_id = routeId;
        this.rp_name = rpName;
        this.priority = priority;
        this.visit_time = visitTime;
    }
}