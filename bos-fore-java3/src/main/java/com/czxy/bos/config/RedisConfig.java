package com.czxy.bos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    public StringRedisTemplate redisTemplate(RedisConnectionFactory connectionFactory){
        StringRedisTemplate srt = new StringRedisTemplate();
        srt.setConnectionFactory(connectionFactory);
        return srt;
    }


}
