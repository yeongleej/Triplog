package com.example.travel.route.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
public class RouteCreateDto implements Serializable {

    private final Long uid;

    private final Long city_id;

    private final Long key_id;

    private final String route_name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate route_day;

    private final MultipartFile image;

    @Builder
    public RouteCreateDto(Long uid, Long city_id, Long key_id, String route_name, @JsonFormat(
            shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate route_day, MultipartFile image) {
        this.uid = uid;
        this.city_id = city_id;
        this.key_id = key_id;
        this.route_name = route_name;
        this.route_day = route_day;
        this.image = image;
    }
}