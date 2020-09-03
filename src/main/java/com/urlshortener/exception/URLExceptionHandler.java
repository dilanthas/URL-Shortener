package com.urlshortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Exception handler for the application
 */
@ControllerAdvice
public class URLExceptionHandler {

    @ExceptionHandler(value
            = { URLShortenerException.class })
    protected ResponseEntity<String> handleWalletException(
            URLShortenerException ex, WebRequest request) {
        HttpStatus status = HttpStatus.valueOf(ex.getErrorCode());
        Map<String, Object> extraInfo = new HashMap<>();
        extraInfo.put("extraInfo",request.getDescription(false));

        return new ResponseEntity<>(ex.getMessage(), status);
    }
}
