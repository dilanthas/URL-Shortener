package com.urlshortener.service;


import com.urlshortener.converter.Base62UrlConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class UrlConverterTest {

    private Base62UrlConverter converter;

    @Before
    public void init(){
        converter = new Base62UrlConverter();
    }

    @Test
    public void shouldReturnCorrectEncodedLength(){

        // Given
        long value = 65;

        // When
        String encodedString = converter.convertToShortUrl(value);

        // Then
        Assertions.assertEquals(encodedString.length() , 7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenIdExceedRange(){
        // Given
        long value = 372036854775807L;

        //When

        converter.convertToShortUrl(value);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenIdLower(){
        // Given
        long value = -1;

        //When

        converter.convertToShortUrl(value);
    }

    @Test
    public void shouldGiveCorrectOutputWhenIdIs1(){
        // Given
        long value = 1;

        // When
        String encodedString = converter.convertToShortUrl(value);

        // Then
        Assertions.assertEquals(encodedString , "aaaaaab");

    }

    @Test
    public void shouldGiveCorrectOutputWhenIdIs65(){
        // Given
        long value = 65;

        // When
        String encodedString = converter.convertToShortUrl(value);

        // Then
        Assertions.assertEquals(encodedString , "aaaaabd");
    }

    @Test
    public void shouldGiveCorrectOutputWhenIdIs45678898976(){
        // Given
        long value = 45678898976L;

        // When
        String encodedString = converter.convertToShortUrl(value);

        // Then
        Assertions.assertEquals(encodedString , "aX1watg");
    }
}
