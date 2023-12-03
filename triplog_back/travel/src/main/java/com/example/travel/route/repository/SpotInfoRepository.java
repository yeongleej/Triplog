package com.example.travel.route.repository;

import com.example.travel.route.entity.SpotInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface SpotInfoRepository {

    List<SpotInfo> findAllSpotInfo();
    SpotInfo findSpotInfoById(Long spot_id);
    List<SpotInfo> findSpotInfoByName(String spotName) throws SQLException;
    int addSpotInfo(List<SpotInfo> spotInfoList);
    int addOneSpotInfo(SpotInfo spotInfo) throws DataAccessException;
    int delete(Long spot_id);
    int modify(SpotInfo spot);
}