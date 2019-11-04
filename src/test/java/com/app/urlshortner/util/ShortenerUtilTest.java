package com.app.urlshortner.util;

import org.junit.Assert;
import org.junit.Test;

public class ShortenerUtilTest {

    String VALID_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789";

    @Test
    public void testGetRandomalphaNumericString_size6() {
        String shortString = ShortenerUtil.getRandomalphaNumericString(6);
        Assert.assertEquals(6, shortString.length());
        for (int i = 0; i < shortString.length(); i++) {
            VALID_CHARS.contains(shortString.substring(i, i));
        }
    }


    @Test
    public void testGetRandomalphaNumericString_boundary() {
        String shortString = ShortenerUtil.getRandomalphaNumericString(1);
        Assert.assertEquals(1, shortString.length());
        for (int i = 0; i < shortString.length(); i++) {
            VALID_CHARS.contains(shortString.substring(i, i));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetRandomalphaNumericString_negativeLength() {
        ShortenerUtil.getRandomalphaNumericString(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetRandomalphaNumericString_ZeroLength() {
        ShortenerUtil.getRandomalphaNumericString(0);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testisValidKeyShort() {
        ShortenerUtil.isValidKey("test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testisValidKeynonAlpaNum() {
        ShortenerUtil.isValidKey("tes!2t");
    }


    @Test(expected = IllegalArgumentException.class)
    public void testisValidURLInvalid() {
        ShortenerUtil.isValidURL("tes45t", "http://www.localhost:8080");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testisValidURLInvalidWithServicePrefix() {
        ShortenerUtil.isValidURL("http://www.localhost:8080/tes45t", "http://www.localhost:8080");
    }

    @Test
    public void testisValidURLvalid() {
        Assert.assertTrue(ShortenerUtil.isValidURL("http://www.anotherhost:8080/tes45t",
                        "http://www.localhost:8080"));
    }
}
