package com.urlshortener.controller;

import com.urlshortener.dto.UrlShortenerCriteriaDTO;
import com.urlshortener.exception.URLShortenerException;
import com.urlshortener.service.URLService;
import com.urlshortener.service.URLServiceImpl;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class URLController {

    Logger LOGGER = LoggerFactory.getLogger(URLController.class);

    private URLService urlService;

    @Autowired
    public URLController(URLService urlService){
       this.urlService = urlService;
    }

    @GetMapping(value = "/hello", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String test() {
        return "Hello!";
    }

    @PostMapping(value = "/shortenurl", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getShortenUrl(@RequestBody UrlShortenerCriteriaDTO criteriaDTO, HttpServletRequest request) {
        UrlValidator validator = new UrlValidator();
        String longUrl = criteriaDTO.getLongUrl();
        LOGGER.info("Received long url:"+longUrl);

        if(validator.isValid(longUrl)){

            // Extract the base URL from the service. ex:- http://localhost:8080/shortenurl will give http://localhost:8080
            String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                    .replacePath(null)
                    .build()
                    .toUriString();

            String shortUrl = urlService.getShortenURL(longUrl,baseUrl);
            return new ResponseEntity<>(shortUrl, HttpStatus.OK);
        }
        return new ResponseEntity<>("Incorrect URL format:"+longUrl, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{url}", method= RequestMethod.GET)
    public RedirectView getLongUrl(@PathVariable String url) throws Exception{
        LOGGER.info("Received short url:"+url);
        String longUrl = urlService.getLongUrl(url);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(longUrl);
        return redirectView;
    }
}
