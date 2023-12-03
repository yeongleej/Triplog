package com.example.travel.board.repository;
import com.example.travel.board.entity.Board;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface BoardRepository {
    void writeArticle(Board board) throws SQLException;

    List<Board> listArticle(Map<String, Object> param) throws SQLException;

    int getTotalArticleCount(Map<String, Object> param) throws SQLException;

    Board getArticle(Long articleNo) throws SQLException;

    void updateHit(Long articleNo) throws SQLException;

    void modifyArticle(Board board) throws SQLException;

    void deleteArticle(Long articleNo) throws SQLException;
}
