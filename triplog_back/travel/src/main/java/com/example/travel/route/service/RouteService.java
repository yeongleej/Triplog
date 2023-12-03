package com.example.travel.route.service;

import com.example.travel.route.dto.request.RouteCreateDto;
import com.example.travel.route.dto.response.RouteResponseDto;
import com.example.travel.route.entity.Image;
import com.example.travel.route.entity.Route;
import com.example.travel.route.entity.RouteImage;
import com.example.travel.route.repository.ImageRepository;
import com.example.travel.route.repository.RouteImageRepository;
import com.example.travel.route.repository.RouteRepository;
import com.example.travel.route.S3.S3Uploader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class RouteService {

    private final S3Uploader uploader;
    private RouteRepository routeRepository;
    private ImageRepository imageRepository;
    private RouteImageRepository routeImageRepository;

    public RouteService(S3Uploader uploader, RouteRepository routeRepository, ImageRepository imageRepository, RouteImageRepository routeImageRepository) {
        this.uploader = uploader;
        this.routeRepository = routeRepository;
        this.imageRepository = imageRepository;
        this.routeImageRepository = routeImageRepository;
    }

    public List<RouteResponseDto> findAllRoute(){
        List<RouteResponseDto> route = routeRepository.findAllRoute();
        System.out.println("All Routes: " + route.size());

        return routeRepository.findAllRoute();
    }

    public RouteResponseDto findRouteById(Long route_id){
        return routeRepository.findRouteById(route_id);
    }
    public List<RouteResponseDto> findRouteByName(String route_name) {
        return routeRepository.findRouteByName(route_name);
    }
    public List<RouteResponseDto> findRouteByKeyName(String keyword){
        return routeRepository.findRouteByKeyName(keyword);
    }
    public List<RouteResponseDto> findRouteByKeyNameTop3(String keyword){
        return routeRepository.findRouteByKeyNameTop3(keyword);
    }
    public List<RouteResponseDto> findRouteByCityName(String city_name){
        return routeRepository.findRouteByCityName(city_name);
    }

    public List<RouteResponseDto> findRouteByKeyword(Long key_id){
        return routeRepository.findRouteByKeyword(key_id);
    }

    public List<RouteResponseDto> findRouteByUser(Long uid){
        return routeRepository.findRouteByUser(uid);
    }
    public List<RouteResponseDto> findAllRouteByUser(Long uid) {return routeRepository.findAllRouteByUser(uid);}
    public List<RouteResponseDto> findRouteBySpot(String spot_name){
        return routeRepository.findRouteBySpot(spot_name);
    }

    public Long addRoute(RouteCreateDto route) throws IOException {
        MultipartFile img = route.getImage();
        String imgUrl = uploader.uploadFiles(img, "route");
        String imgName = img.getOriginalFilename();
        String imgKey = img.getContentType();
        Image image = Image.builder().img_name(imgName).img_key(imgKey).img_url(imgUrl).build();
        imageRepository.saveImage(image);
        Route newRoute = Route.builder().uid(route.getUid()).city_id(route.getCity_id())
                        .key_id(route.getKey_id()).route_name(route.getRoute_name())
                        .route_day(route.getRoute_day()).build();

        // Route Image 테이블에도 저장하는 코드 추가
        routeRepository.addRoute(newRoute);
        Long route_id = newRoute.getRoute_id();
//        System.out.println("route_id >> "+route_id+" img_id >> "+img_id);
        RouteImage newRouteImage = RouteImage.builder().route_id(route_id).img_id(image.getImg_id()).img_url(imgUrl).build();
        System.out.println(newRouteImage);
        routeImageRepository.saveRouteImage(newRouteImage);
        return route_id;
    }

    public int delete(Long route_id){
        return routeRepository.delete(route_id);
    }

    public int modify(Route route){
        return routeRepository.modify(route);
    }
}