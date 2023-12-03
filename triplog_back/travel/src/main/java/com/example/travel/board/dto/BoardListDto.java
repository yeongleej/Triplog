package com.example.travel.board.dto;

import java.util.List;
import com.example.travel.board.entity.Board;

public class BoardListDto {

    private List<Board> articles;
    private int currentPage;
    private int totalPageCount;

    public List<Board> getArticles() {
        return articles;
    }

    public void setArticles(List<Board> articles) {
        this.articles = articles;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

}
