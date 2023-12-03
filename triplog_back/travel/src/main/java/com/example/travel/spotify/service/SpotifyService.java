package com.example.travel.spotify.service;

import com.example.travel.spotify.config.SpotifyConfig;
import com.example.travel.spotify.dto.SpotifySearchResponseDto;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.data.browse.GetRecommendationsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetAudioFeaturesForTrackRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SpotifyService {
//    private SpotifyApi spotifyApi = new SpotifyApi.Builder()
//            .setAccessToken(SpotifyConfig.accessToken())
//            .build();

    public SpotifyService() {
    }

    public List<SpotifySearchResponseDto> searchByKeyword(Integer type, String keyword){
        try {
            SpotifyApi spotifyApi = new SpotifyApi.Builder()
                    .setAccessToken(SpotifyConfig.getInstance().accessToken())
                    .build();

            List<SpotifySearchResponseDto> spotifyResponseList = new ArrayList<>();
            Track[] tracks = null;
            //검색 기능 api(1), 추전 기능 api(2)
            if(type == 1){
                SearchTracksRequest searchTrackRequest = spotifyApi.searchTracks(keyword)
                        .limit(10)
                        .build();

                Paging<Track> searchResult = searchTrackRequest.execute();
                tracks = searchResult.getItems();
            } else {
                GetRecommendationsRequest recommendationsRequest = spotifyApi.getRecommendations()
                        .limit(10)
                        .min_popularity(5)
                        .seed_genres(keyword)
                        .build();
                Recommendations searchResult = recommendationsRequest.execute();
                tracks = searchResult.getTracks();
            }

            // api to Json(dto)
            for(Track track: tracks){
                GetAudioFeaturesForTrackRequest getA = spotifyApi.getAudioFeaturesForTrack(track.getId()).build();
                AudioFeatures searchResult =  getA.execute();

                // 음악 재생 시간
                Integer duration_ms = searchResult.getDurationMs();
                Integer duration_ss = (int) Math.ceil((duration_ms * 0.001)%60);
                Integer duration_mm = (int) Math.round((duration_ms*0.001)/60);

                //음악 제목
                String title = track.getName();

                AlbumSimplified album = track.getAlbum();
                ArtistSimplified[] artists = album.getArtists();

                //아티스트 이름
                String artistName = artists[0].getName();

                //앨범 커버 url
                Image[] images = album.getImages();
                String imageUrl = (images.length > 0) ? images[0].getUrl() : "https://mytriplog.s3.us-west-2.amazonaws.com/route/ffwyxno-music.png";

                //앨범 이름
                String albumName = album.getName();

                // 음악 링크
                Map<String, String> externalUrlMap =  album.getExternalUrls().getExternalUrls();
                String externalUrl = externalUrlMap.get("spotify");

                // 미리듣기 링크
                String  previewUrl = track.getPreviewUrl();

                // 추천수
                Integer popularity =  track.getPopularity();
                SpotifySearchResponseDto search = SpotifySearchResponseDto.builder()
                        .title(title)
                        .artistName(artistName)
                        .albumName(albumName)
                        .imageUrl(imageUrl)
                        .externalUrl(externalUrl)
                        .previewUrl(previewUrl)
                        .popularity(popularity)
                        .duration_mm(duration_mm)
                        .duration_ss(duration_ss).build();
                spotifyResponseList.add(search);

//                System.out.println("title : "+title);
//                System.out.println("artistName : "+artistName);
//                System.out.println("imgUrl : "+imageUrl);
//                System.out.println("albumName : "+albumName);
//                System.out.println("externalUrl : "+externalUrl);
//                System.out.println("previewUrl : "+previewUrl);
//                System.out.println("popularity : "+popularity);
//                System.out.println();
            }
            return spotifyResponseList;

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
