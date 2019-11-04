package com.app.urlshortner.controller;

import java.time.Clock;
import java.time.ZonedDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.app.urlshortner.model.URLShortenerResponse;
import com.app.urlshortner.model.URLShortenerResponseTest;

public class ErrorResponeseTest {

    @Test
    public void testSetGet() {
        ErrorResponse response1 =
                        new ErrorResponse(ZonedDateTime.now(Clock.systemUTC()), HttpStatus.INTERNAL_SERVER_ERROR,
                                        HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error : ErrorMessage");
        Assert.assertEquals(response1.getMessage(), "Error : ErrorMessage");
        Assert.assertEquals(response1.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        Assert.assertEquals(response1.getStatusName(), HttpStatus.INTERNAL_SERVER_ERROR.value());

        response1.setMessage("ChangedMessage");
        response1.setStatus(HttpStatus.BAD_REQUEST);
        response1.setStatusName(HttpStatus.BAD_REQUEST.value());

        Assert.assertEquals(response1.getMessage(), "ChangedMessage");
        Assert.assertEquals(response1.getStatus(), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(response1.getStatusName(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testEquals() {
        URLShortenerResponse response1 = new URLShortenerResponse("hostedURL", "shortURL");
        URLShortenerResponse response2 = new URLShortenerResponse("hostedURL", "shortURL");
        Assert.assertTrue(response1.equals(response2));
    }

    @Test
    public void testEquals_others() {
        URLShortenerResponse response1 = new URLShortenerResponse(null, "test");
        URLShortenerResponse response2 = new URLShortenerResponse("test", null);
        Assert.assertFalse(response1.equals(response2));
        Assert.assertFalse(response1.equals(null));
        Assert.assertTrue(response1.equals(response1));
        response1 = new URLShortenerResponse("hostedURL", "shortURL1");
        Assert.assertFalse(response1.equals(response2));
        response1 = new URLShortenerResponse("hostedURL1", "shortURL");
        Assert.assertFalse(response1.equals(response2));
        response1 = new URLShortenerResponse(null, null);
        response2 = new URLShortenerResponse(null, null);
        Assert.assertTrue(response1.equals(response2));
        Assert.assertFalse(response1.equals(new URLShortenerResponseTest()));



    }

    @Test
    public void testHashCode() {
        URLShortenerResponse response1 = new URLShortenerResponse("hostedURL", "shortURL");
        URLShortenerResponse response2 = new URLShortenerResponse("hostedURL", "shortURL");
        Assert.assertTrue(response1.hashCode() == response2.hashCode());
    }


    @Test
    public void testHashCode_null() {
        URLShortenerResponse response1 = new URLShortenerResponse(null, null);
        URLShortenerResponse response2 = new URLShortenerResponse(null, null);
        Assert.assertTrue(response1.hashCode() == response2.hashCode());
    }

}
