package com.bmc.cloudresource.service.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.app.urlshortner.model.URLShortenRequest;
import com.app.urlshortner.model.URLShortenerResponse;
import com.app.urlshortner.service.ShortenerServiceImpl;
import com.app.urlshortner.service.dao.ShortenerDAO;
import com.app.urlshortner.util.ShortenerUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ShortenerUtil.class)
public class ShortenerServiceTest {

    @Mock
    private ShortenerDAO shortenerDAO;

    @Mock
    private ShortenerUtil shortenerUtil;

    private ShortenerServiceImpl service;

    private URL hostedUrl;

    @Before
    public void setUp() throws MalformedURLException {
        hostedUrl = new URL("http://localhost:8080");
        service = new ShortenerServiceImpl(shortenerDAO, 6, hostedUrl);
    }


    @Test
    public void testShorten() {
        PowerMockito.mockStatic(ShortenerUtil.class);
        Mockito.doReturn(1).when(shortenerDAO).saveURLMapping(Mockito.anyString(), Mockito.anyString());
        Mockito.when(ShortenerUtil.getRandomalphaNumericString(Mockito.anyInt())).thenReturn("abcdefg");
        URLShortenRequest request = new URLShortenRequest("https://www.test.com");
        Assert.assertEquals(new URLShortenerResponse(hostedUrl.toString(), "abcdefg"), service.shorten(request));
    }

    @Test
    public void testShorten_duplicate() {
        PowerMockito.mockStatic(ShortenerUtil.class);
        Mockito.when(shortenerDAO.saveURLMapping(Mockito.anyString(), Mockito.anyString())).thenReturn(0)
                        .thenReturn(1);
        Mockito.when(ShortenerUtil.getRandomalphaNumericString(Mockito.anyInt())).thenReturn("abcdefg")
                        .thenReturn("abcde1");
        URLShortenRequest request = new URLShortenRequest("https://www.test.com");
        Assert.assertEquals(new URLShortenerResponse(hostedUrl.toString(), "abcde1"), service.shorten(request));
    }


    @Test
    public void testGetOrigURL() {
        Mockito.doReturn(Optional.of("http://www.test.com")).when(shortenerDAO).getOrigURL(Mockito.anyString());
        Assert.assertEquals(Optional.of("http://www.test.com"), service.getOrigURL("abcdefg"));
    }
}
