package com.urlshortener;

import com.urlshortener.converter.Base62UrlConverter;
import com.urlshortener.converter.UrlConverter;
import com.urlshortener.service.RandomNumberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
