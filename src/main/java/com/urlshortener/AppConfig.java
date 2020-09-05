package com.urlshortener;

import com.urlshortener.converter.Base62UrlConverter;
import com.urlshortener.converter.UrlConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class AppConfig {

    @Bean
    public UrlConverter urlConverter(){
        return new Base62UrlConverter();
    }



}
