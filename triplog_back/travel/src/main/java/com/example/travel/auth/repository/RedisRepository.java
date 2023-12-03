package com.example.travel.auth.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setValues(String email, String rtk) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(email, rtk);
    }

    public void setValues(String email, String rtk, Duration duration) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(email, rtk, duration);
    }

    public String getValues(String email) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(email);
    }

    public void deleteValues(String email) {
        redisTemplate.delete(email);
    }
}