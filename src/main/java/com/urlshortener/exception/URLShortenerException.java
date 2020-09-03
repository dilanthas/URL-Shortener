package com.urlshortener.exception;

public class URLShortenerException extends Exception {

    private int errorCode;

    public URLShortenerException(String message,int errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
