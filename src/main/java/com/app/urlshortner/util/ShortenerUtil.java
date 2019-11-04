package com.app.urlshortner.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.urlshortner.model.ReportRequest;

public class ShortenerUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ShortenerUtil.class);
    private static final String VALID_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789";

    /**
     * Utility method to provide arbitrary String of {@link ShortenerUtil#VALID_CHARS} of
     * <code> length </code> more than 0
     * 
     * @param length should be greater than 0
     * @return
     * @exception java.lang.IllegalArgumentException if length is less than 1
     */
    public static String getRandomalphaNumericString(int length) {
        LOG.info("generating aplphanumneric String of length{}", length);
        if (length > 0) {
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int index = (int) (VALID_CHARS.length() * Math.random());
                sb.append(VALID_CHARS.charAt(index));
            }
            return sb.toString();
        } else {
            LOG.error("Invalid Request to generate aplphanumneric String of length{} ", length);
            throw new IllegalArgumentException("length should be greater than 0");
        }
    }

    /**
     * Utility method to check if the provided URL key is valid or not
     * 
     * @param key provided key for the URL
     * @return
     * @exception java.lang.IllegalArgumentException if key contains a character not in [A-z, a-z
     *            and 1-0]
     */
    public static boolean isValidKey(String key) {
        LOG.info("Validate Request key {} revceived", key);

        if (key == null || key.length() < 6) {
            LOG.error("URL Key should not be less than 6 chars");
            throw new IllegalArgumentException("URL Key should not be less than 6 chars");
        }
        for (int i = 0; i < key.length(); i++) {
            if (!VALID_CHARS.contains(key.substring(i, i + 1))) {
                LOG.error("URL Key should contain characters between 0-9, A-Z and a-z");
                throw new IllegalArgumentException("URL Key should contain characters between 0-9, A-Z and a-z");
            }
        }
        return true;
    }

    /**
     * Utility method to validate a URL
     * 
     * @param url Input URL to be validated
     * @return true is the URL is valid
     * @exception java.lang.IllegalArgumatException is the URL is not valid
     */
    public static boolean isValidURL(String url, String hostedURL) {
        LOG.info("Validate Request for URL {} revceived with hostedURl as {}", url, hostedURL);
        URI uri;
        try {
            uri = new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            LOG.error("Input URL is not valid, ", e);
            throw new IllegalArgumentException("Input URL is not valid, " + e.getMessage(), e);
        }
        if (uri.toString().startsWith(hostedURL)) {
            LOG.error("Input URL is cannot start with the URL of this service");
            throw new IllegalArgumentException("Input URL is cannot start with the URL of this service");
        }
        return true;
    }

    /**
     * Validates if the ReportRequest object is valid, specifically the sort_on and sort_order
     * fields
     * 
     * @param reportRequest
     * @return
     */
    // TODO this method needs to be implemented
    public static boolean isValidReportRequest(ReportRequest reportRequest) {
        return true;
    }
}
