package com.example.travel.route.repository;

import com.example.travel.route.entity.Cities;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CitiesRepository {

    int addCities(Cities city);

    Cities findCitiesById(Long city_id);

    List<Cities> findAllCities();

    int delete(Long city_id);

    int modify(Cities city);
}