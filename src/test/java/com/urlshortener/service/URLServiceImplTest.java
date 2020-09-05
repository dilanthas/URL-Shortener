package com.urlshortener.service;

import com.urlshortener.converter.UrlConverter;
import com.urlshortener.exception.URLShortenerException;
import com.urlshortener.repository.URLRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class URLServiceImplTest {


    @MockBean
    UrlConverter converter;

    @MockBean
    URLRepository repository;

    URLServiceImpl urlService;

    private String baseUrl = "http://localhost:8080";

    @Before
    public void ini(){

        urlService = new URLServiceImpl(converter,repository);

    }

    @Test
    public void shouldReturnExistingShortUrl(){

        // Given
        String longUrl = "www.test.com";

        Mockito.when(repository.getShortUrl(longUrl)).thenReturn("abc");
        // When
        String shortUrl = urlService.getShortenURL(longUrl,baseUrl);

        // Then
        Assert.assertEquals(shortUrl,baseUrl+"/abc");
    }

    @Test
    public void shouldReturnShortUrlInCorrectFormat(){

        // Given
        String longUrl = "www.test.com";
        Mockito.when(repository.incrementID()).thenReturn(123L);
        Mockito.when(converter.convertToShortUrl(123L)).thenReturn("cde");


        // When
        String shortUrl = urlService.getShortenURL(longUrl,baseUrl);

        // Then
        Assert.assertEquals(shortUrl,baseUrl+"/cde");

    }

    @Test(expected = URLShortenerException.class)
    public void shouldThrowExceptionWhenUrlDoesNotExists() throws Exception{

        // Given
        String shortUrl = "abc";
        Mockito.when(repository.getLongUrl(shortUrl)).thenReturn(null);

        // When
        urlService.getLongUrl("abc");
        // Then
    }

    @Test
    public void shouldReturnCorrectLongUrl() throws Exception{

        //Given
        String shortUrl = "abc";
        String expectedLongUrl = "www.test.com";

        Mockito.when(repository.getLongUrl(shortUrl)).thenReturn(expectedLongUrl);

        // When
        String longUrl = urlService.getLongUrl(shortUrl);

        // Then
        Assert.assertEquals(longUrl,expectedLongUrl);
    }


}
