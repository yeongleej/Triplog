package com.example.travel.route.controller;

import com.example.travel.route.entity.KeywordList;
import com.example.travel.route.service.KeywordListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/keyword-list")
public class KeywordListController {

    private final KeywordListService service;

    public KeywordListController(KeywordListService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> findAllKeywordList() {
        List<KeywordList> keywordList = service.findAllKeywordList();

        if(keywordList != null && !keywordList.isEmpty()) {
            return new ResponseEntity<>(keywordList, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }

    }
}
