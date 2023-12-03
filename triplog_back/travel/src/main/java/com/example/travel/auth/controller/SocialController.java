package com.example.travel.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/login")
public class SocialController {

    @GetMapping("/token/")
    public ResponseEntity<Object> kakaoLogin(@RequestParam("acc-tk") String accessToken) throws URISyntaxException  {
        URI redirectUri = new URI("http://localhost:5173");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);

        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }
}