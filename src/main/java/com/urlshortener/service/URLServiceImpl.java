package com.urlshortener.service;

import com.urlshortener.converter.UrlConverter;
import com.urlshortener.exception.URLShortenerException;
import com.urlshortener.model.Url;
import com.urlshortener.repository.URLRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Service
public class URLServiceImpl implements URLService {

    private static final Logger LOGGER = LoggerFactory.getLogger(URLServiceImpl.class);

    private UrlConverter converter;
    private RandomNumberService randomNumberService;
    private URLRepository urlRepository;

    @Autowired
    public URLServiceImpl(UrlConverter converter, RandomNumberService randomNumberService, URLRepository urlRepository) {
        this.converter = converter;
        this.randomNumberService = randomNumberService;
        this.urlRepository = urlRepository;
    }

    @Override
    public String getShortenURL(String longUrl, String localUrl) {

        LOGGER.info("Checking for already encoded url :" + longUrl);
        // First check whether the url already exists
        Url url = urlRepository.findByLongUrl(longUrl);

        if (url != null) {
            LOGGER.info("Url already exists in the database :" + longUrl);
            return localUrl + "/" + url.getShortUrl();
        } else {

            LOGGER.info("Url does not found in the database. Converting url:" + longUrl);
            String encodedUrl = converter.convertToShortUrl(randomNumberService.getRandomNumber());

            LOGGER.info("Long url :" + longUrl + ",Converted to:" + encodedUrl);
            //String baseUrl = getBaseUrlFromLocalUrl(localUrl);

            Url newUrl = new Url(new Date(), longUrl, encodedUrl);
            urlRepository.save(newUrl);

            return localUrl + "/" + encodedUrl;
        }

    }

    @Override
    public String getLongUrl(String shortUrl) throws URLShortenerException {
        Optional<Url> urlOpt = urlRepository.findById(shortUrl);
        if(urlOpt.isPresent()){
            return urlOpt.get().getLongUrl();
        }else{
            throw new URLShortenerException("URL does not exists: "+shortUrl,404);
        }

    }


    private String getBaseUrlFromLocalUrl(String localURL) {
        String[] addressComponents = localURL.split("/");
        // remove the endpoint (last index)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < addressComponents.length - 1; ++i) {
            sb.append(addressComponents[i]);
        }
        sb.append('/');
        return sb.toString();
    }
}
