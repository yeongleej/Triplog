package com.example.travel.route.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SpotInfoDto {

    private final String spotName;
    private final String address;
    private final Double longitude;
    private final Double latitude;

    @Builder
    public SpotInfoDto(String spotName, String address, Double longitude, Double latitude) {
        this.spotName = spotName;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}