package com.app.urlshortner.model;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportRow {

    @JsonProperty("short_url")
    private String shortURL;
    @JsonProperty("long_url")
    private String longURL;
    @JsonProperty("redirects_count")
    private int redirectCount;
    @JsonProperty("crated_at")
    private long createdAt;

    @JsonProperty("last_redirect_at")
    private Optional<Long> lastRedirectAt;

    public String getShortURL() {
        return shortURL;
    }

    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }

    public String getLongURL() {
        return longURL;
    }

    public void setLongURL(String longURL) {
        this.longURL = longURL;
    }

    public int getRedirectCount() {
        return redirectCount;
    }

    public void setRedirectCount(int i) {
        this.redirectCount = i;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public Optional<Long> getLastRedirectAt() {
        return lastRedirectAt;
    }

    public void setLastRedirectAt(Optional<Long> lastRedirectAt) {
        this.lastRedirectAt = lastRedirectAt;
    }
}
