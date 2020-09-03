package com.urlshortener.service;


import com.urlshortener.exception.URLShortenerException;

public interface URLService {

    String getShortenURL(String longUrl, String localUrl);

    String getLongUrl(String shortUrl) throws URLShortenerException;

}