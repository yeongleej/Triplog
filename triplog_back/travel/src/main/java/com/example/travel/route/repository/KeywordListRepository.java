package com.example.travel.route.repository;

import com.example.travel.route.entity.KeywordList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KeywordListRepository {

    List<KeywordList> findAllKeywordList();
    KeywordList findKeywordListById(Long key_id);
    List<Long> findKeywordListByName(List<String> keyNames);
    int addkeywordList(KeywordList key);
    int delete(Long key_id);
    int modify(KeywordList key);
}