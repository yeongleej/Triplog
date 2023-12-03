package com.example.travel.auth.repository;

import com.example.travel.auth.entity.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, String> {

    Token findByEmail(String email);

    @Override
    <S extends Token> S save(S entity);
}