package com.app.urlshortner.model;

import org.junit.Assert;
import org.junit.Test;

public class URLShortenRequestTest {

    @Test
    public void testSetGet() {
        URLShortenRequest request1 = new URLShortenRequest("testLongURL");
        Assert.assertEquals(request1.getURL(), "testLongURL");
        request1 = new URLShortenRequest();
        request1.setURL("test2URl");
        Assert.assertEquals(request1.getURL(), "test2URl");
    }

    @Test
    public void testEquals() {
        URLShortenRequest request1 = new URLShortenRequest("LongURL");
        URLShortenRequest request2 = new URLShortenRequest("LongURL");
        Assert.assertTrue(request1.equals(request2));
    }

    @Test
    public void testEquals_others() {
        URLShortenRequest request1 = new URLShortenRequest("URLShortenRequest");
        URLShortenRequest request2 = new URLShortenRequest(null);
        Assert.assertFalse(request1.equals(request2));
        Assert.assertFalse(request1.equals(null));
        Assert.assertTrue(request1.equals(request1));
        request2 = new URLShortenRequest("URLShortenRequest2");
        Assert.assertFalse(request1.equals(request2));

        request1 = new URLShortenRequest(null);
        request2 = new URLShortenRequest(null);
        Assert.assertTrue(request1.equals(request2));
        Assert.assertFalse(request2.equals(new URLShortenRequestTest()));
    }

    @Test
    public void testHashCode() {
        URLShortenRequest request1 = new URLShortenRequest("LongURL");
        URLShortenRequest request2 = new URLShortenRequest("LongURL");
        Assert.assertTrue(request1.hashCode() == request2.hashCode());
    }


    @Test
    public void testHashCode_null() {
        URLShortenRequest request1 = new URLShortenRequest(null);
        URLShortenRequest request2 = new URLShortenRequest(null);
        Assert.assertTrue(request1.hashCode() == request2.hashCode());
    }

}
