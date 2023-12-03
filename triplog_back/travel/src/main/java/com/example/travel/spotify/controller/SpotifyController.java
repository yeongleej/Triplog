package com.example.travel.spotify.controller;

import com.example.travel.spotify.dto.SpotifySearchResponseDto;
import com.example.travel.spotify.service.SpotifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@Controller
@RequestMapping("/spotify")
@CrossOrigin("*")
@Slf4j
public class SpotifyController {
    private SpotifyService spotifyService;

    public SpotifyController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping("/{type}/{keyword}")
    public ResponseEntity<?> searchByKeyword(@PathVariable("type")Integer type, @PathVariable("keyword") String keyword){
//        System.out.println(keyword);
        List<SpotifySearchResponseDto> responseList = spotifyService.searchByKeyword(type, keyword);
        if(responseList != null && !responseList.isEmpty()) {
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }
}
