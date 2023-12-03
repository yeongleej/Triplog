package com.example.travel.route.service;

import com.example.travel.route.entity.KeywordList;
import com.example.travel.route.repository.KeywordListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordListService {

    private KeywordListRepository kdao;

    public KeywordListService(KeywordListRepository kdao) {
        this.kdao = kdao;
    }

    public List<KeywordList> findAllKeywordList(){
        return kdao.findAllKeywordList();
    }
    public KeywordList findKeywordListById(Long key_id){
        return kdao.findKeywordListById(key_id);
    }
    public List<Long> findKeywordListByName(List<String> keyNames){
        return kdao.findKeywordListByName(keyNames);
    }
    public int addkeywordList(KeywordList key){
        return kdao.addkeywordList(key);
    }
    public int delete(Long key_id){
        return kdao.delete(key_id);
    }
    public int modify(KeywordList key){
        return kdao.modify(key);
    }
}