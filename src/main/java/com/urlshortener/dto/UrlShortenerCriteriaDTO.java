package com.urlshortener.dto;

import com.urlshortener.constant.ErrorConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class UrlShortenerCriteriaDTO {

    @NotNull(message = "longUrl" + ErrorConstants.CANNOT_BE_EMPTY)
    private String longUrl;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
