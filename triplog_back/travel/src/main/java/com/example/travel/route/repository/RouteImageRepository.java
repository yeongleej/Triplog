package com.example.travel.route.repository;

import com.example.travel.route.entity.RouteImage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RouteImageRepository {
    @Insert("insert into routeimage(route_id, img_id, img_url) values(#{route_id}, #{img_id}, #{img_url} )")
    void saveRouteImage(RouteImage routeImage);
}
