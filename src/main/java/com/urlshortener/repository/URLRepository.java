package com.urlshortener.repository;

public interface URLRepository {

    Long incrementID();

    void saveUrl(String shortUrl, String longUrl);

    String getLongUrl(String shortUrl);

    String getShortUrl(String longUrl);
}
