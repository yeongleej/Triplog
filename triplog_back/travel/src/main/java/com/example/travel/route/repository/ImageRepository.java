package com.example.travel.route.repository;

import com.example.travel.route.entity.Image;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.SQLException;

@Mapper
public interface ImageRepository {

    @Select("select img_id, img_name from image where img_url=#{img_url}")
    Image findImageByUrl(String img_url) throws SQLException;

    // @Insert("insert into image(img_name, img_key, img_url) values(#{img_name}, #{img_key}, #{img_url})")
    Long saveImage(Image image);
}