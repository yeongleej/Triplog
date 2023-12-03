package com.example.travel.route.service;

import com.example.travel.route.dto.request.PlaceModifyDto;
import com.example.travel.route.dto.request.PlaceRegisterDto;
import com.example.travel.route.dto.response.PlaceResponseDto;
import com.example.travel.route.dto.response.ReviewResponseDto;
import com.example.travel.route.dto.response.RouteResponseDto;
import com.example.travel.route.entity.Review;
import com.example.travel.route.entity.ReviewImage;
import com.example.travel.route.entity.RoutePlace;
import com.example.travel.route.entity.SpotInfo;
import com.example.travel.route.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoutePlaceService {

    @Value("${app.kakao.rest-api-key}")
    private String kakaoApiKey;

    private final SpotInfoRepository sdao;
    private final RouteRepository rdao;
    private final RoutePlaceRepository pdao;
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository mappingRepository;

    public RoutePlaceService(SpotInfoRepository sdao, RouteRepository rdao, RoutePlaceRepository pdao, ReviewRepository reviewRepository, ReviewImageRepository mappingRepository) {
        this.sdao = sdao;
        this.rdao = rdao;
        this.pdao = pdao;
        this.reviewRepository = reviewRepository;
        this.mappingRepository = mappingRepository;
    }

    public List<RoutePlace> findAllRoutePlace(){
        return pdao.findAllRoutePlace();
    }

    public PlaceResponseDto findRoutePlaceById(Long place_id) {
        RoutePlace rp = pdao.findRoutePlaceById(place_id);
        SpotInfo sp = sdao.findSpotInfoById(rp.getSpot_id());
        RouteResponseDto rt = rdao.findRouteById(rp.getRoute_id());

        String placeName = sp.getSpot_name();
        String address = sp.getAddress();
        String routeName = rt.getRoute_name();
        PlaceResponseDto placeResponse = PlaceResponseDto.builder()
                .place_id(rp.getPlace_id())
                .placeName(placeName).address(address).routeName(routeName)
                .priority(rp.getPriority()).visitTime(rp.getVisit_time())
                .longitude(sp.getLongitude()).latitude(sp.getLatitude()).build();

        return placeResponse;
    }

    public List<PlaceResponseDto> findRoutePlaceByRoute(Long route_id) throws SQLException {
        List<PlaceResponseDto> responseDtoList = new ArrayList<>();
        List<RoutePlace> rpList = pdao.findRoutePlaceByRoute(route_id);

        // 해당 루트의 리뷰들 찾기
        List<ReviewResponseDto> reviewList = new ArrayList<>();
        List<Review> reviews = reviewRepository.findByRoute(route_id);
        for(Review review: reviews){
            List<ReviewImage> reviewImages = mappingRepository.findReviewImages(review.getReview_id());
            ReviewResponseDto response = ReviewResponseDto.builder().review_id(review.getReview_id())
                    .place_id(review.getPlace_id()).review_text(review.getReview_text())
                    .imageList(reviewImages).build();
            reviewList.add(response);
        }

        // 헤당 루트의 response List (세부장소 + 리뷰)
        for(RoutePlace rp : rpList){
            SpotInfo sp = sdao.findSpotInfoById(rp.getSpot_id());
            RouteResponseDto rt = rdao.findRouteById(rp.getRoute_id());
            String address = sp.getAddress();
            String routeName = rt.getRoute_name();
            boolean isReview = false;
            PlaceResponseDto placeResponse = null;
            for(ReviewResponseDto review : reviewList){
                if (review.getPlace_id() == rp.getPlace_id()){
                    placeResponse = PlaceResponseDto.builder()
                            .place_id(rp.getPlace_id())
                            .placeName(rp.getRp_name()).address(address).routeName(routeName)
                            .priority(rp.getPriority()).visitTime(rp.getVisit_time())
                            .review_id(review.getReview_id()).review_text(review.getReview_text())
                            .imageList(review.getImageList())
                            .longitude(sp.getLongitude()).latitude(sp.getLatitude()).build();
                    isReview = true;
                }
            }
            if (!isReview){
                placeResponse = PlaceResponseDto.builder()
                        .place_id(rp.getPlace_id())
                        .placeName(rp.getRp_name()).address(address).routeName(routeName)
                        .priority(rp.getPriority()).visitTime(rp.getVisit_time())
                        .longitude(sp.getLongitude()).latitude(sp.getLatitude()).build();
            }
//            System.out.println("newRP => "+ placeResponse.toString());
            responseDtoList.add(placeResponse);
        }

        return responseDtoList;
    }

    // 루트에 장소 추가
    public PlaceResponseDto registerRoutePlace(Long routeId, PlaceRegisterDto requestDto) throws Exception {
        RoutePlace routePlace = RoutePlace.builder().route_id(routeId).spot_id(requestDto.getSpot_id())
                .rp_name(requestDto.getRp_name()).priority(requestDto.getPriority())
                .visit_time(requestDto.getVisit_time()).build();

        pdao.addRoutePlace(routePlace);

        RouteResponseDto rt = rdao.findRouteById(routePlace.getRoute_id());
        System.out.println("Spot id 있니??? " + requestDto.getSpot_id());
        SpotInfo sp = sdao.findSpotInfoById(routePlace.getSpot_id());

        String placeName = routePlace.getRp_name();
        String address = sp.getAddress();
        String routeName = rt.getRoute_name();

        PlaceResponseDto responseDto = PlaceResponseDto.builder()
                .place_id(routePlace.getPlace_id()).placeName(placeName)
                .address(address).priority(routePlace.getPriority()).routeName(routeName)
                .visitTime(routePlace.getVisit_time())
                .longitude(sp.getLongitude()).latitude(sp.getLatitude()).build();
        return responseDto;
    }

    public int modify(PlaceRegisterDto place) {
        RoutePlace rp = RoutePlace.builder().build();

        return pdao.modify(rp);
    }

     @Transactional
     public List<PlaceResponseDto> deletePlace(Long route_id, Long place_id, Integer priority) throws SQLException {
         // 1. 해당 RoutePlace 삭제
         pdao.deleteById(place_id);

         List<PlaceResponseDto> resultList = new ArrayList<>();
         // 2. 삭제된 후 RoutePlace 리스트 가져옴
         List<RoutePlace> places = pdao.findRoutePlaceByRoute(route_id);


         for(RoutePlace p : places){
             PlaceResponseDto placeResponse = null;
             RouteResponseDto rt = rdao.findRouteById(route_id);
             SpotInfo sp = sdao.findSpotInfoById(p.getSpot_id());


             // 3. 삭제된 RoutePlace의 priority보다 크다면 1씩 감소 update
             if (p.getPriority() > priority) {
                 PlaceModifyDto placeModifyDto = PlaceModifyDto.builder().place_id(p.getPlace_id())
                         .priority(p.getPriority() - 1).build();
                 pdao.modifyPriorityById(placeModifyDto);
                 placeResponse = PlaceResponseDto.builder()
                         .place_id(p.getPlace_id())
                         .placeName(p.getRp_name()).address(p.getRp_name()).routeName(rt.getRoute_name())
                         .priority(p.getPriority()-1).visitTime(p.getVisit_time())
                         .longitude(sp.getLongitude()).latitude(sp.getLatitude()).build();

             } else{    // 3-1. 이전 priority인 경우
                 placeResponse = PlaceResponseDto.builder()
                         .place_id(p.getPlace_id())
                         .placeName(p.getRp_name()).address(p.getRp_name()).routeName(rt.getRoute_name())
                         .priority(p.getPriority()).visitTime(p.getVisit_time())
                         .longitude(sp.getLongitude()).latitude(sp.getLatitude()).build();
             }
             // 반환할 RoutePlace 객체 삽입
             resultList.add(placeResponse);
         }

         // 해당 루트의 리뷰들 찾기
         List<ReviewResponseDto> reviewList = new ArrayList<>();
         List<Review> reviews = reviewRepository.findByRoute(route_id);
         for(Review review: reviews){
             List<ReviewImage> reviewImages = mappingRepository.findReviewImages(review.getReview_id());
             ReviewResponseDto response = ReviewResponseDto.builder().review_id(review.getReview_id())
                     .place_id(review.getPlace_id()).review_text(review.getReview_text())
                     .imageList(reviewImages).build();
             reviewList.add(response);
         }

         // 리뷰 포함해서 다시 리스트업
         List<PlaceResponseDto> realList = new ArrayList<>();
         for(PlaceResponseDto rp: resultList) {
             boolean isReview = false;
             PlaceResponseDto placeRealResponse = null;
             for(ReviewResponseDto review : reviewList){
                 if (review.getPlace_id() == rp.getPlace_id()){
                     placeRealResponse = PlaceResponseDto.builder()
                             .place_id(rp.getPlace_id())
                             .placeName(rp.getPlaceName()).address(rp.getAddress()).routeName(rp.getRouteName())
                             .priority(rp.getPriority()).visitTime(rp.getVisitTime())
                             .review_id(review.getReview_id()).review_text(review.getReview_text())
                             .imageList(review.getImageList())
                             .longitude(rp.getLongitude()).latitude(rp.getLatitude()).build();
                     isReview = true;
                 }
             }
             if (!isReview){
                 placeRealResponse = rp;
             }
//            System.out.println("newRP => "+ placeResponse.toString());
             realList.add(placeRealResponse);
         }

         return realList;
     }
}