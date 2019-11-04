package com.app.urlshortner.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import com.app.urlshortner.model.URLShortenRequest;
import com.app.urlshortner.model.URLShortenerResponse;
import com.app.urlshortner.service.ShortenerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShortenerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ShortenerService shortnerService;

    @InjectMocks
    private ShortenerController shortenerController;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(shortenerController).build();
    }

    @Test
    public void testShorten() throws Exception {

        URLShortenRequest urlShortenRequest = new URLShortenRequest("https://www.test.com");
        URLShortenerResponse response = new URLShortenerResponse("https://localhost:8080", "acded8");
        Mockito.when(shortnerService.shorten((URLShortenRequest) any())).thenReturn(response);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON).content(objectToJson(urlShortenRequest));

        mockMvc.perform(builder).andExpect(status().isOk());
    }

    @Test
    public void testResolve() throws Exception {

        Mockito.when(shortnerService.getOrigURL(anyString())).thenReturn(Optional.of("https://www.test.com"));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/rd/acded8");

        mockMvc.perform(builder).andExpect(status().isMovedPermanently())
                        .andExpect(redirectedUrl("https://www.test.com"));
    }



    @Test(expected = NestedServletException.class)
    public void testResolve_negative() throws Exception {

        Optional<String> opt = Optional.empty();
        Mockito.when(shortnerService.getOrigURL(anyString())).thenReturn(opt);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/rd/notavl");

        mockMvc.perform(builder);
    }

    @Test(expected = NestedServletException.class)
    public void testResolve_negative2() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/rd/short");
        mockMvc.perform(builder);
    }

    private byte[] objectToJson(Object object) {
        byte[] result = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
