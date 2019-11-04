package com.app.urlshortner.service.dao;

import java.util.List;
import java.util.Optional;

import com.app.urlshortner.model.ReportRequest;
import com.app.urlshortner.model.ReportRow;

public interface ShortenerDAO {

    static enum SortOrder {
        ASC, DESC;
    }

    public int saveURLMapping(String sortURL, String origURL);

    public Optional<String> getOrigURL(String sortURL);

    List<ReportRow> getReport(ReportRequest reportRequest);


}
