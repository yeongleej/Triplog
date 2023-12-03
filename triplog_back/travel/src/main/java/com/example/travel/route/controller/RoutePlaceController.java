package com.example.travel.route.controller;

import com.example.travel.route.dto.request.PlaceRegisterDto;
import com.example.travel.route.dto.response.PlaceResponseDto;
import com.example.travel.route.entity.RoutePlace;
import com.example.travel.route.service.RoutePlaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/place")
public class RoutePlaceController {
    private final RoutePlaceService placeService;

    public RoutePlaceController(RoutePlaceService placeService) {
        this.placeService = placeService;
    }

    // 루트 세부 장소 전체 조회
    @GetMapping
    public ResponseEntity<?> findAllRoutePlace() {
        try {
            List<RoutePlace> placeList = placeService.findAllRoutePlace();

            if(placeList != null && !placeList.isEmpty()) {
                return new ResponseEntity<>(placeList, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    // 루트 세부 장소 생성
    @PostMapping("/{routeId}")
    public ResponseEntity<?> addRoutePlace(@PathVariable("routeId") Long routeId, @RequestBody PlaceRegisterDto place) {
        try {
            PlaceResponseDto ans = placeService.registerRoutePlace(routeId, place);
            return new ResponseEntity<>(ans, HttpStatus.OK);
//			return new ResponseEntity<Integer>(cnt, HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    // 루트 세부 장소 정보 조회 - 세부 장소 번호
    @GetMapping("/{placeId}")
    public ResponseEntity<?> findRoutePlaceById(@PathVariable("placeId") Long placeId) {
        try {
            PlaceResponseDto routePlace = placeService.findRoutePlaceById(placeId);

            if(routePlace != null) {
                return new ResponseEntity<>(routePlace, HttpStatus.OK);
//				return new ResponseEntity<List<MemberDto>>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    // 루트 세부 장소 조회 - 루트 번호
    @GetMapping("/{routeId}/all")
    public ResponseEntity<?> findRoutePlaceByRoute(@PathVariable("routeId") Long routeId) {
        try {
            List<PlaceResponseDto> placeList = placeService.findRoutePlaceByRoute(routeId);
            if(placeList != null && !placeList.isEmpty()) {
                return new ResponseEntity<>(placeList, HttpStatus.OK);
//				return new ResponseEntity<List<MemberDto>>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    // 루트 세부 장소 정보 수정
    @PutMapping
    public ResponseEntity<?> routePlaceModify(@RequestBody PlaceRegisterDto place) {
        try {
            int ans = placeService.modify(place);
            return new ResponseEntity<>(ans, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    // 루트 세부 장소 정보 삭제
    @DeleteMapping("/{route_id}/{place_id}/{priority}")
    public ResponseEntity<?> routePlaceDelete(@PathVariable("route_id") Long route_id, @PathVariable("place_id") Long place_id, @PathVariable("priority") Integer priority) {
        try {
            List<PlaceResponseDto> changeList = placeService.deletePlace(route_id, place_id, priority);
            if(changeList != null && !changeList.isEmpty()) {
                return new ResponseEntity<>(changeList, HttpStatus.OK);
//				return new ResponseEntity<List<MemberDto>>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }


    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}