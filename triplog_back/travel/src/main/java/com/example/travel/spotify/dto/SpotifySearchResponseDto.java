package com.example.travel.spotify.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SpotifySearchResponseDto {
    private String title;
    private String artistName;
    private String imageUrl;
    private String albumName;
    private String externalUrl;
    private String previewUrl;
    private Integer popularity;
    private Integer duration_mm;
    private Integer duration_ss;

    @Builder

    public SpotifySearchResponseDto(String title, String artistName, String imageUrl, String albumName, String externalUrl, String previewUrl, Integer popularity, Integer duration_mm, Integer duration_ss) {
        this.title = title;
        this.artistName = artistName;
        this.imageUrl = imageUrl;
        this.albumName = albumName;
        this.externalUrl = externalUrl;
        this.previewUrl = previewUrl;
        this.popularity = popularity;
        this.duration_mm = duration_mm;
        this.duration_ss = duration_ss;
    }
}
