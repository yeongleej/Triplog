package com.example.travel.route.repository;

import com.example.travel.route.dto.request.PlaceModifyDto;
import com.example.travel.route.entity.RoutePlace;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoutePlaceRepository {

    List<RoutePlace> findAllRoutePlace();
    RoutePlace findRoutePlaceById(Long place_id);
    List<RoutePlace> findRoutePlaceByRoute(Long route_id);
    int addRoutePlace(RoutePlace place);
    int deleteById(Long place_id);

    int modifyPriorityById(PlaceModifyDto placeModifyDto);
    int delete(Long place_id);
    int modify(RoutePlace place);
}