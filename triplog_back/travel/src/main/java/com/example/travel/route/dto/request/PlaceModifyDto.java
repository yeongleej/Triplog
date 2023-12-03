package com.example.travel.route.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceModifyDto {
    private final Long place_id;
    private final Integer priority;

    @Builder
    public PlaceModifyDto(Long place_id, Integer priority) {
        this.place_id = place_id;
        this.priority = priority;
    }
}
