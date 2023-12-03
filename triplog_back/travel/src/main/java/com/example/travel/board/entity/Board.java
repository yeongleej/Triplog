package com.example.travel.board.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Board {

    private Long articleNo;

    private Long uid;

    private String nickname;

    private String subject;

    private String content;

    private int hit;

    private String registerTime;

    public Board() {
    }

    @Builder
    public Board(Long uid, String subject, String content) {
        this.uid = uid;
        this.subject = subject;
        this.content = content;
    }

    @Builder
    public Board(Long uid, String nickname, String subject, String content, int hit, String registerTime) {
        this.uid = uid;
        this.nickname = nickname;
        this.subject = subject;
        this.content = content;
        this.hit = hit;
        this.registerTime = registerTime;
    }

    @Builder
    public Board(Long articleNo, Long uid, String nickname, String subject, String content,
                 int hit, String registerTime) {
        this.articleNo = articleNo;
        this.uid = uid;
        this.nickname = nickname;
        this.subject = subject;
        this.content = content;
        this.hit = hit;
        this.registerTime = registerTime;
    }
}