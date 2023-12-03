package com.example.travel.route.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SpotInfo {

    private Long spot_id;

    private String spot_name;

    private String address;

    private double longitude;

    private double latitude;

    @Builder
    public SpotInfo(Long spot_id, String spot_name, String address,  double longitude, double latitude) {
        this.spot_id = spot_id;
        this.spot_name = spot_name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Builder
    public SpotInfo(String spot_name, double longitude, double latitude) {
        this.spot_name = spot_name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Builder
    public SpotInfo(String spot_name, String address, double longitude, double latitude) {
        this.spot_name = spot_name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Builder
    public SpotInfo(Long spot_id, String spot_name) {
        this.spot_id = spot_id;
        this.spot_name = spot_name;
    }
}