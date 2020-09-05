package com.urlshortener.converter;

/**
 * Base 62 encoder for url
 */
public class Base62UrlConverter implements UrlConverter  {

    private static final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int URL_LENGTH = 7;
    private final int BASE = 62;
    private final double MAX_LIMIT = Math.pow(allowedString.length(), 7);

    public String convertToShortUrl(long id) throws IllegalArgumentException {
        if (id < 0 || id > MAX_LIMIT) {
            throw new IllegalArgumentException("Out of range input value : " + id);
        }
        return getBase62EncodedString(id);
    }

    /**
     * Convert the given value to base 62 number and encode them with allowed characters. Length of the encoded string is 7
     *
     * @param value
     * @return - encoded string
     */
    private String getBase62EncodedString(long value) {
        StringBuilder sb = new StringBuilder();
        while (value != 0) {
            sb.append(allowedString.charAt((int) (value % BASE)));
            value /= BASE;
        }
        while (sb.length() < URL_LENGTH) {
            sb.append(allowedString.charAt(0));
        }
        return sb.reverse().toString();
    }
}
