package com.example.travel.route.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SpotRequestDto {

    private final String spot_name;
    private final Double longitude;
    private final Double latitude;

    @Builder
    public SpotRequestDto(String spot_name, Double longitude, Double latitude) {
        this.spot_name = spot_name;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}