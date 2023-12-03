package com.example.travel.route.service;

import com.example.travel.route.entity.Cities;
import com.example.travel.route.repository.CitiesRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class CitiesService {

    private CitiesRepository cdao;
    public CitiesService(CitiesRepository cdao) {
        this.cdao = cdao;
    }

    public int addCities(Cities city){
        return cdao.addCities(city);
    }

    public Cities findCitiesById(Long city_id){
        return cdao.findCitiesById(city_id);
    }

    public List<Cities> findAllCities() throws SQLException {
        return cdao.findAllCities();
    }

    public int delete(Long city_id){
        return cdao.delete(city_id);
    }

    public int modify(Cities city){
        return  cdao.modify(city);
    }
}