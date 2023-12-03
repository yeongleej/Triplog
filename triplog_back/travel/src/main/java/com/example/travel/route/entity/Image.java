package com.example.travel.route.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Image {

    private Long img_id;

    private String img_name;

    private String img_key;

    private String img_url;

    public Image() {}

    @Builder
    public Image(String img_name, String img_key, String img_url) {
        this.img_name = img_name;
        this.img_key = img_key;
        this.img_url = img_url;
    }

    @Builder
    public Image(Long img_id, String img_name) {
        this.img_id = img_id;
        this.img_name = img_name;
    }
}