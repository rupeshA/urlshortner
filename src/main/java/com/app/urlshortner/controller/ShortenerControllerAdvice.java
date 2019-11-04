package com.app.urlshortner.controller;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class ShortenerControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ShortenerControllerAdvice.class);

    @InitBinder
    private void activateDirectFieldAccess(DataBinder dataBinder) {
        dataBinder.initDirectFieldAccess();
    }


    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex,
                    HttpServletRequest request) {
        String msg = String.format("[%s]: IllegalArgumentException - ", request.getContextPath());
        LOG.error(msg, ex);
        return new ResponseEntity<>(new ErrorResponse(ZonedDateTime.now(Clock.systemUTC()), HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex, HttpServletRequest request) {
        String errorId = UUID.randomUUID().toString();
        String msg = String.format("Exception : Error Id:[%s]", errorId);
        LOG.error(msg, ex);
        return new ResponseEntity<>(
                        new ErrorResponse(ZonedDateTime.now(Clock.systemUTC()), HttpStatus.INTERNAL_SERVER_ERROR,
                                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                        "Error : " + ex.getMessage() + ". (Error Id:" + errorId + ")."),
                        HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
