package com.example.travel.route.S3.upload;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface S3ImageRepository {

    @Insert("insert into s3image(s3_url) values(#{url})")
    void saveS3(String url) throws SQLException;
}