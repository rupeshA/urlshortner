package com.app.urlshortner.service;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.urlshortner.model.ReportRequest;
import com.app.urlshortner.model.ReportRow;
import com.app.urlshortner.model.URLShortenRequest;
import com.app.urlshortner.model.URLShortenerResponse;
import com.app.urlshortner.service.dao.ShortenerDAO;
import com.app.urlshortner.util.ShortenerUtil;

@Service
public class ShortenerServiceImpl implements ShortenerService {

    private static final Logger LOG = LoggerFactory.getLogger(ShortenerService.class);
    private ShortenerDAO shortenerDAO;

    public final int urlKeySize;
    private final URL hostedURL;

    @Autowired
    public ShortenerServiceImpl(ShortenerDAO shortenerDAO, @Value("${key.size}") int size,
                    @Value("${hosted.url}") URL hostedURL) {
        this.shortenerDAO = shortenerDAO;
        this.urlKeySize = size;
        this.hostedURL = hostedURL;
    }

    public URLShortenerResponse shorten(URLShortenRequest request) {
        LOG.info("Shortening request received for URL {}", request.getURL());
        ShortenerUtil.isValidURL(request.getURL(), hostedURL.toString());
        String shortURL = ShortenerUtil.getRandomalphaNumericString(urlKeySize);
        if (shortenerDAO.saveURLMapping(shortURL, request.getURL().toString()) == 0) {
            return shorten(request);
        } else {
            return new URLShortenerResponse(hostedURL.toString(), shortURL);
        }
    }

    public Optional<String> getOrigURL(String shortUrl) throws IllegalArgumentException {
        LOG.info("Redirect request received for shortURL {}", shortUrl);
        return shortenerDAO.getOrigURL(shortUrl);

    }

    @Override
    public List<ReportRow> getReport(ReportRequest reportRequest) {
        LOG.info("ReportRequest Received {}", reportRequest);
        return shortenerDAO.getReport(reportRequest);

    }

}
