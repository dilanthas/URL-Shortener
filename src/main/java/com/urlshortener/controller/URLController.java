package com.urlshortener.controller;

import com.urlshortener.dto.UrlShortenerCriteriaDTO;
import com.urlshortener.service.URLService;
import com.urlshortener.service.URLServiceImpl;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@RestController
public class URLController {
    Logger LOGGER = LoggerFactory.getLogger(URLController.class);

    private URLService urlService;

    @Autowired
    public URLController(URLServiceImpl urlService){
       this.urlService = urlService;
    }

    @PostMapping(value = "/shortenurl", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getShortenUrl(@RequestBody UrlShortenerCriteriaDTO criteriaDTO, HttpServletRequest request) throws Exception{
        UrlValidator validator = new UrlValidator();
        String longUrl = criteriaDTO.getLongUrl();
        if(validator.isValid(longUrl)){

            String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                    .replacePath(null)
                    .build()
                    .toUriString();
            return urlService.getShortenURL(longUrl,baseUrl);
        }
        throw new Exception("Invalid url"+longUrl);
    }
}
