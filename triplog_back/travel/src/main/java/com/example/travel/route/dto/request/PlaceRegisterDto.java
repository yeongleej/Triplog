package com.example.travel.route.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PlaceRegisterDto {

    private final Long spot_id;
    private final String rp_name;
    private final Double longitude;
    private final Double latitude;
    private final Integer priority;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private final LocalDateTime visit_time;

    @Builder
    public PlaceRegisterDto(Long spot_id,String rp_name, Double longitude, Double latitude,
                                   Integer priority, @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss") LocalDateTime visit_time) {
        this.spot_id = spot_id;
        this.rp_name = rp_name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.priority = priority;
        this.visit_time = visit_time;
    }
}