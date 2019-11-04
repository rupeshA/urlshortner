package com.app.urlshortner.service;


import java.util.List;
import java.util.Optional;

import com.app.urlshortner.model.ReportRequest;
import com.app.urlshortner.model.ReportRow;
import com.app.urlshortner.model.URLShortenRequest;
import com.app.urlshortner.model.URLShortenerResponse;

public interface ShortenerService {

    public URLShortenerResponse shorten(URLShortenRequest request);

    public Optional<String> getOrigURL(String shortUrl);

    List<ReportRow> getReport(ReportRequest reportRequest);
}
