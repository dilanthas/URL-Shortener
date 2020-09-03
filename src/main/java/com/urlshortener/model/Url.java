package com.urlshortener.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;

@RedisHash("Url")
public class Url implements Serializable {


    @Id
    private String shortUrl;

    private Date createdDate;

    private String longUrl;



    public Url(){

    }

    public Url(Date createdDate,String longUrl,String shortUrl){
        this.createdDate = createdDate;
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
