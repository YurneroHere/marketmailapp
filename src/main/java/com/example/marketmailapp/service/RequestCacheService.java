package com.example.marketmailapp.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RequestCacheService {

    private final Map<Long, String> cache = new ConcurrentHashMap<>();

    public boolean isDuplicate(Long key) {
        return cache.containsKey(key);
    }

    public void store(Long key, String response) {
        cache.put(key, response);
    }

    public String removeResponse(Long key) {
        return cache.remove(key);
    }

    public String getResponse(Long key) {
        return cache.get(key);
    }

}
