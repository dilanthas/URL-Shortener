package com.urlshortener.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

@Repository
public class RedisURLRepository implements URLRepository {

    private final Jedis jedis;
    private String id;
    private String sUrlKey;
    private String lUrlKey;


    @Autowired
    public RedisURLRepository(Environment environment){
        this.jedis = new Jedis(environment.getProperty("spring.redis.host"));
        this.id = "id";
        this.sUrlKey = "surl";
        this.lUrlKey = "lurl";
    }

    @Override
    public Long incrementID() {
        Long id = jedis.incr(this.id);
        return id ;
    }

    /**
     * When saving we are saving under two keys, one to query short urls and the otherone to query long urls
     * @param shortUrl
     * @param longUrl
     */
    @Override
    public void saveUrl(String shortUrl, String longUrl) {
        jedis.hset(sUrlKey, shortUrl, longUrl);
        jedis.hset(lUrlKey, longUrl, shortUrl);

    }

    @Override
    public String getLongUrl(String shortUrl) {
        String url = jedis.hget(this.sUrlKey, shortUrl);
        return url;
    }

    @Override
    public String getShortUrl(String longUrl){
        String url = jedis.hget(this.lUrlKey, longUrl);
        return url;
    }
}
