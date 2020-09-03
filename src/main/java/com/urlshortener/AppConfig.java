package com.urlshortener;

import com.urlshortener.converter.Base62UrlConverter;
import com.urlshortener.converter.UrlConverter;
import com.urlshortener.service.RandomNumberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class AppConfig {

    @Bean
    public UrlConverter urlConverter(){
        return new Base62UrlConverter();
    }

    @Bean
    public RandomNumberService randomNumberService(){
        return new RandomNumberService();
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

}
