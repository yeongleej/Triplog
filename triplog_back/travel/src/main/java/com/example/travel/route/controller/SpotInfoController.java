package com.example.travel.route.controller;

import com.example.travel.route.entity.SpotInfo;
import com.example.travel.route.service.SpotInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/spot")
public class SpotInfoController {

    private final SpotInfoService spotInfoService;

    public SpotInfoController(SpotInfoService spotInfoService) {
        this.spotInfoService = spotInfoService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllSpotInfo() {
        try {
            List<SpotInfo> spotInfoList = spotInfoService.findAllSpotInfo();
            if(spotInfoList != null && !spotInfoList.isEmpty()) {
                return new ResponseEntity<>(spotInfoList, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/{spotId}")
    public ResponseEntity<?> findSpotInfoById(@PathVariable("spotId") Long spotId){
        try {
            SpotInfo spot = spotInfoService.findSpotInfoById(spotId);
            if(spot != null) {
                return new ResponseEntity<>(spot, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> findSpotInfoByName(@RequestParam String spotName,
                                                @RequestParam Double longitude, @RequestParam Double latitude) {
        try {
            List<SpotInfo> spotInfoList = spotInfoService.findSpotInfoByName(spotName, longitude, latitude);

            if(spotInfoList != null && !spotInfoList.isEmpty()) {
                return new ResponseEntity<>(spotInfoList, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/again")
    public ResponseEntity<?> findSpotInfoAgain(@RequestParam String spotName,
                                               @RequestParam Double longitude, @RequestParam Double latitude) {
        try {
            List<SpotInfo> spotInfoList = spotInfoService.findSpotInfoAgain(spotName, longitude, latitude);

            if(spotInfoList != null && !spotInfoList.isEmpty()) {
                return new ResponseEntity<>(spotInfoList, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        log.info(e.getMessage());
        return new ResponseEntity<>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}