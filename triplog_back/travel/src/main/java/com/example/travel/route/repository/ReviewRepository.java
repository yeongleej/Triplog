package com.example.travel.route.repository;

import com.example.travel.route.entity.Review;
import org.apache.ibatis.annotations.*;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface ReviewRepository {

    @Select("select review_id, place_id, review_text from review where review_id=#{review_id}")
    Review findById(Long review_id) throws SQLException;

    @Select("select review_id, place_id, review_text from review where place_id=#{place_id} and review_text=#{review_text}")
    Review findByPlaceText(Long place_id, String review_text) throws SQLException;

    @Select("select r.review_id, r.place_id, r.review_text\n" +
            "from review r\n" +
            "join route_place p\n" +
            "on r.place_id = p.place_id\n" +
            "where p.route_id = #{route_id} ")
    List<Review> findByRoute(Long route_id) throws SQLException;

    @Insert("insert into review(place_id, review_text) values(#{place_id}, #{review_text})")
    int saveReview(Review review) throws SQLException;

    @Update("update review set review_text=#{review_text} where review_id=#{review_id}")
    int modifyText(Long review_id, String review_text) throws SQLException;

    @Delete("delete from review where review_id=#{review_id}")
    void deleteReview(Long review_id) throws SQLException;
}