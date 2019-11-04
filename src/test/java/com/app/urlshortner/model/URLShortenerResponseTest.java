package com.app.urlshortner.model;

import org.junit.Assert;
import org.junit.Test;

public class URLShortenerResponseTest {

    @Test
    public void testSetGet() {
        URLShortenerResponse response1 = new URLShortenerResponse("hostedURL", "shortURL");
        Assert.assertEquals(response1.getShortenedURL(), "hostedURL" + "shortURL");
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
