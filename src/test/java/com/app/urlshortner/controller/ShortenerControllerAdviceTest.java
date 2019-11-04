package com.app.urlshortner.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

public class ShortenerControllerAdviceTest {

    private ShortenerControllerAdvice advice = new ShortenerControllerAdvice();

    @Test
    public void testHandleShortenerRuntimeException() {
        IllegalArgumentException ex = new IllegalArgumentException("TestException");
        ResponseEntity<Object> response = advice.handleIllegalArgumentException(ex, new MockHttpServletRequest());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testHandleShortenerException() {
        Exception ex = new Exception("DB Unavailabale");
        ResponseEntity<Object> response = advice.handleException(ex, new MockHttpServletRequest());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
