package com.urlshortener.service;

import com.urlshortener.converter.UrlConverter;
import com.urlshortener.model.Url;
import com.urlshortener.repository.URLRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class URLServiceImpl implements URLService {

    private static final Logger LOGGER = LoggerFactory.getLogger(URLServiceImpl.class);

    private UrlConverter converter;
    private RandomNumberService randomNumberService;
    private URLRepository urlRepository;

    @Autowired
    public URLServiceImpl(UrlConverter converter, RandomNumberService randomNumberService,URLRepository urlRepository){
        this.converter = converter;
        this.randomNumberService = randomNumberService;
        this.urlRepository = urlRepository;
    }

    public String getShortenURL(String longUrl,String localUrl){

        String encodedUrl = converter.convertToShortUrl(randomNumberService.getRandomNumber());
        String baseUrl = getBaseUrlFromLocalUrl(localUrl);

        Url url = new Url(new Date(),longUrl,encodedUrl);

        urlRepository.save(url);
        return localUrl+"/"+encodedUrl;
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
