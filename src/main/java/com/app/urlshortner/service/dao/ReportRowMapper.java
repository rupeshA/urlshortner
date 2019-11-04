package com.app.urlshortner.service.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import com.app.urlshortner.model.ReportRow;

public class ReportRowMapper implements RowMapper<ReportRow> {

    @Override
    public ReportRow mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReportRow row = new ReportRow();
        row.setCreatedAt(rs.getTimestamp("created_at").getTime());
        Optional<Long> accessDate = rs.getTimestamp("last_resolved_at") == null ? Optional.empty()
                        : Optional.of(rs.getTimestamp("last_resolved_at").getTime());
        row.setLastRedirectAt(accessDate);
        row.setRedirectCount(rs.getInt("resolved_count"));
        row.setShortURL(rs.getString("short_url"));
        row.setLongURL(rs.getString("orig_url"));
        return row;
    }
}
