package com.app.urlshortner.controller;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    ZonedDateTime time;
    HttpStatus status;
    int statusCode;
    String message;

    public ErrorResponse(ZonedDateTime time, HttpStatus status, int statusCode, String message) {
        this.time = time;
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public int getStatusName() {
        return statusCode;
    }

    public void setStatusName(int statusName) {
        this.statusCode = statusName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
