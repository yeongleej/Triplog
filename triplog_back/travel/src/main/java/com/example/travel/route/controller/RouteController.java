package com.example.travel.route.controller;

import com.example.travel.route.dto.request.RouteCreateDto;
import com.example.travel.route.dto.response.RouteResponseDto;
import com.example.travel.route.entity.Route;
import com.example.travel.route.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@Controller
@RequestMapping("/route")
@CrossOrigin("*")
@Slf4j
public class RouteController {
    private RouteService routeService;
//    private KeywordListService kservice;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    // 전체 루트 조회
    @GetMapping
    public ResponseEntity<?> findAllRoute() {
        try {
            List<RouteResponseDto> routeList = routeService.findAllRoute();
            System.out.println(routeList);
            if(routeList != null && !routeList.isEmpty()) {
                return new ResponseEntity<>(routeList, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    // 루트 조회 - 루트 번호
    @GetMapping("/{routeId}")
    public ResponseEntity<?> findRouteById(@PathVariable("routeId") Long routeId) {
        try {
            RouteResponseDto route = routeService.findRouteById(routeId);
            if(route != null) {
                return new ResponseEntity<RouteResponseDto>(route, HttpStatus.OK);
//				return new ResponseEntity<List<MemberDto>>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }
    // 루트 조회 - 루트 이름
    @GetMapping("/name/{routeName}")
    public ResponseEntity<?> findRouteByName(@PathVariable("routeName") String routeName) {
        try {
            List<RouteResponseDto> routeList = routeService.findRouteByName(routeName);
            if(routeList != null && !routeList.isEmpty()) {
                return new ResponseEntity<>(routeList, HttpStatus.OK);
//				return new ResponseEntity<List<MemberDto>>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }
    // 루트 조회 - 키워드 이름
    @GetMapping("/keyname/{keyword}")
    public ResponseEntity<?> findRouteByKeyName(@PathVariable("keyword") String keyword) {
        try {
            List<RouteResponseDto> routeList = routeService.findRouteByKeyName(keyword);
            if(routeList != null && !routeList.isEmpty()) {
                return new ResponseEntity<>(routeList, HttpStatus.OK);
//				return new ResponseEntity<List<MemberDto>>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }
    // 루트 조회 - 키워드 이름 - top 3 in 메인화면
    @GetMapping("/keyname/{keyword}/3")
    public ResponseEntity<?> findRouteByKeyNameTop3(@PathVariable("keyword") String keyword) {
        try {
            List<RouteResponseDto> routeList = routeService.findRouteByKeyNameTop3(keyword);
            if(routeList != null && !routeList.isEmpty()) {
                return new ResponseEntity<>(routeList, HttpStatus.OK);
//				return new ResponseEntity<List<MemberDto>>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }
    // 루트 조회 - 도시 이름
    @GetMapping("/cityname/{cityName}")
    public ResponseEntity<?> findRouteByCityName(@PathVariable("cityName") String cityName) {
        try {
            List<RouteResponseDto> routeList = routeService.findRouteByCityName(cityName);
            if(routeList != null && !routeList.isEmpty()) {
                return new ResponseEntity<>(routeList, HttpStatus.OK);
//				return new ResponseEntity<List<MemberDto>>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }
    // 루트 조회 - 사용자 3개만
    @GetMapping("/user/{uid}")
    public ResponseEntity<?> findRouteByUser(@PathVariable("uid") Long uid) {
        try {
            List<RouteResponseDto> routeList = routeService.findRouteByUser(uid);
            if(routeList != null && !routeList.isEmpty()) {
                log.info("일단 데이터는 나감");
                return new ResponseEntity<>(routeList, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    // 루트 조회 - 사용자 전체 조회
    @GetMapping("/user/{uid}/all")
    public ResponseEntity<?> findAllRouteByUser(@PathVariable("uid") Long uid) {
        try {
            List<RouteResponseDto> routeList = routeService.findAllRouteByUser(uid);
            if(routeList != null && !routeList.isEmpty()) {
                log.info("일단 데이터는 나감");
                return new ResponseEntity<>(routeList, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    // 루트 조회 - 장소
    @GetMapping("/spot-info")
    public ResponseEntity<?> findRouteBySpot(@RequestParam("spotName") String spotName) {
        try {
            List<RouteResponseDto> routeList = routeService.findRouteBySpot(spotName);
            if(routeList != null && !routeList.isEmpty()) {
                return new ResponseEntity<>(routeList, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    // 루트 조회 - 키워드
    @GetMapping("/keyword/{keyId}")
    public ResponseEntity<?> findRouteByKeywords(@PathVariable("keyId") Long keyId) {
        try {
            List<RouteResponseDto> routeList = routeService.findRouteByKeyword(keyId);
            if(routeList != null && !routeList.isEmpty()) {
                return new ResponseEntity<>(routeList, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    // 루트 생성
    @PostMapping
    public ResponseEntity<?> addRoute(@ModelAttribute RouteCreateDto route) {
        try {
            Long ans = routeService.addRoute(route);
            return new ResponseEntity<>(ans, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    // 루트 정보 수정
    @PutMapping
    public ResponseEntity<?> routeModify(@RequestBody Route route) {
        try {
            int ans = routeService.modify(route);
            return new ResponseEntity<Integer>(ans, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    // 루트 정보 삭제
    @DeleteMapping("/{routeId}")
    public ResponseEntity<?> routeDelete(@PathVariable("routeId") Long routeId) {
        try {
            int ans = routeService.delete(routeId);
            return new ResponseEntity<Integer>(ans, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}