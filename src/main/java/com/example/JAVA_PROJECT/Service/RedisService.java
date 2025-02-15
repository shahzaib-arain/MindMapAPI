package com.example.JAVA_PROJECT.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.dynamic.domain.Timeout;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {


    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> entityClas) {
        try {
            Object o = redisTemplate.opsForValue().get(key);

            if (o == null) {
                log.warn("Key '{}' not found in Redis", key);
                return null; // Or return a default value
            }

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(o.toString(), entityClas);

        } catch (Exception e) {
            log.error("Exception occurred while fetching key '{}' from Redis", key, e);
            return null;
        }
    }


    public void set(String key, Object o, Long ttl){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonValue = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key ,jsonValue ,ttl , TimeUnit.SECONDS);
        }
        catch (Exception e){
            log.error("Exception",e);
        }
    }
}
