package com.app.urlshortner.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.urlshortner.model.ReportRequest;
import com.app.urlshortner.model.ReportRow;
import com.app.urlshortner.model.URLShortenRequest;
import com.app.urlshortner.model.URLShortenerResponse;
import com.app.urlshortner.service.ShortenerService;
import com.app.urlshortner.util.ShortenerUtil;

@RestController
@RequestMapping(value = ShortenerController.ROOT)
@Validated
public class ShortenerController {

    private static final Logger LOG = LoggerFactory.getLogger(ShortenerController.class);
    private ShortenerService shortenerService;
    private static final String SHORTEN_PATH = "shorten";
    private static final String REDIRECT_PATH = "rd/{urlKey}";
    public static final String ROOT = "/";
    private static final String REPORT_PATH = "report";



    @Autowired
    public ShortenerController(ShortenerService shortenerService) {
        this.shortenerService = shortenerService;
    }

    @PostMapping(value = SHORTEN_PATH, consumes = "application/json")
    public ResponseEntity<URLShortenerResponse> shorten(@RequestBody URLShortenRequest request) {
        LOG.info("{} POST request received for URL {}", SHORTEN_PATH, request.getURL());
        URLShortenerResponse response = shortenerService.shorten(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = REDIRECT_PATH)
    public ResponseEntity<Object> resolve(@PathVariable String urlKey) {
        LOG.info("GET request received to Resolve the URL {}", urlKey);
        ShortenerUtil.isValidKey(urlKey);
        HttpHeaders headers = new HttpHeaders();
        Optional<String> expandedURL = shortenerService.getOrigURL(urlKey);
        if (expandedURL.isPresent()) {
            headers.setLocation(URI.create(expandedURL.get()));
            return new ResponseEntity<Object>(headers, HttpStatus.MOVED_PERMANENTLY);
        } else {
            throw new IllegalArgumentException(
                            "Provided URL key " + urlKey + " is not valid, No URL found mapped with it");
        }
    }

    @PostMapping(path = REPORT_PATH)
    public ResponseEntity<Object> resolve(@RequestBody ReportRequest reportRequest) {
        LOG.info("POST request received to get report with request {}", reportRequest);
        ShortenerUtil.isValidReportRequest(reportRequest);
        List<ReportRow> response = shortenerService.getReport(reportRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
