package com.example.travel.route.repository;

import com.example.travel.route.entity.ReviewImage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReviewImageRepository {

    @Select("select rv_id, review_id, img_id, img_url from reviewimage where review_id=#{review_id}")
    List<ReviewImage> findReviewImages(Long review_id);

    @Insert("insert into reviewimage(review_id, img_id, img_url) values(#{review_id}, #{img_id}, #{img_url})")
    void saveReviewImage(Long review_id, Long img_id, String img_url);
}
