package com.urlshortener.service;

import com.urlshortener.converter.UrlConverter;
import com.urlshortener.exception.URLShortenerException;
import com.urlshortener.repository.URLRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class URLServiceImpl implements URLService {

    private static final Logger LOGGER = LoggerFactory.getLogger(URLServiceImpl.class);

    private UrlConverter converter;
    private URLRepository urlRepository;

    @Autowired
    public URLServiceImpl(UrlConverter converter, URLRepository urlRepository) {
        this.converter = converter;
        this.urlRepository = urlRepository;
    }

    /**
     * Get short url for the given long url
     * @param longUrl
     * @param localUrl
     * @return
     */
    @Override
    public String getShortenURL(String longUrl, String localUrl) {

        LOGGER.info("Checking for already encoded url :" + longUrl);
        // First check whether the url already exists
        String shortUrl = urlRepository.getShortUrl(longUrl);

        if (shortUrl != null) {
            LOGGER.info("Url already exists in the database :" + longUrl);
            return localUrl + "/" + shortUrl;
        } else {
            Long id = urlRepository.incrementID();
            LOGGER.info("Url was not found in the database. Converting url:" + longUrl);
            String encodedUrl = converter.convertToShortUrl(id);

            LOGGER.info("Long url :" + longUrl + ",Converted to:" + encodedUrl);

            urlRepository.saveUrl(encodedUrl, longUrl);

            return localUrl + "/" + encodedUrl;
        }

    }

    /**
     * Get long url for the given short urls
     * @param shortUrl
     * @return
     * @throws URLShortenerException
     */
    @Override
    public String getLongUrl(String shortUrl) throws URLShortenerException {

        LOGGER.info("Checking for short url :" + shortUrl);
        String longUrl = urlRepository.getLongUrl(shortUrl);
        if (longUrl == null) {
            LOGGER.info("URL not found :" + shortUrl);

            throw new URLShortenerException("URL does not exists: " + shortUrl, 404);
        } else {

            LOGGER.info("Long URL found :" + longUrl);

            return longUrl;
        }

    }

}
