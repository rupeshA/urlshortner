package com.app.urlshortner.service.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.app.urlshortner.model.ReportRequest;
import com.app.urlshortner.model.ReportRow;


@Repository
public class ShortenerDAOImpl implements ShortenerDAO {
    private static final Logger LOG = LoggerFactory.getLogger(ShortenerDAOImpl.class);
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final String SHORT_URL = "shortURL";
    private static final String ORIG_URL = "origURL";
    private static final String OFFSET = "offset";
    private static final String LIMIT = "limit";
    private static final String START_TIME = "startTime";
    private static final String END_TIME = "endTime";
    private static final String ORDER_PLACEHOLDER = "%s";
    private static final String CREATED_AT = "createdAt";
    private static final String LAST_REDIRECT_AT = "redirectAt";
    private static final String URL_MAPPING_TABLE_NAME = "%s.url_mapping";

    private String schemaName;

    private String insertQuery =
                    String.format("INSERT into %s (short_Url, orig_url,created_at) values (:%s, :%s ,:%s)",
                                    URL_MAPPING_TABLE_NAME, SHORT_URL, ORIG_URL, CREATED_AT);

    private String selectQuery = String.format("SELECT orig_url from %s where short_url = :%s; ",
                    URL_MAPPING_TABLE_NAME, SHORT_URL);

    private String updateCountQuery = String.format(
                    "UPDATE %s SET last_resolved_at = :%s, resolved_count = resolved_count + 1 WHERE  short_url = :%s",
                    URL_MAPPING_TABLE_NAME, LAST_REDIRECT_AT, SHORT_URL);

    private String reportQuery = String.format(
                    "SELECT short_url, orig_url, created_at, last_resolved_at, resolved_count "
                                    + "FROM %s WHERE created_at>= :%s AND created_at<= :%s " + " ORDER BY %s "
                                    + "LIMIT :%s OFFSET :%s",

                    URL_MAPPING_TABLE_NAME, START_TIME, END_TIME, ORDER_PLACEHOLDER, LIMIT, OFFSET);

    @Autowired
    public ShortenerDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                    @Value("{schema.name}") String schemaName) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.schemaName = "test";
    }

    public int saveURLMapping(String sortURL, String origURL) {
        LOG.info("Parameters recieved shortUrl: {}, origURL:{}", sortURL, origURL);
        MapSqlParameterSource namedParameters = new MapSqlParameterSource().addValue(SHORT_URL, sortURL)
                        .addValue(ORIG_URL, origURL).addValue(CREATED_AT, Timestamp.from(Instant.now()));
        int count;
        String finalQuery = String.format(insertQuery, schemaName);
        LOG.info("Query: {}", finalQuery);

        try {
            count = namedParameterJdbcTemplate.update(finalQuery, namedParameters);
        } catch (DuplicateKeyException dke) {
            count = 0;
        }
        return count;
    }

    public Optional<String> getOrigURL(String sortURL) {
        LOG.info("Parameters recieved shortUrl: {}", sortURL);

        MapSqlParameterSource namedParameters = new MapSqlParameterSource().addValue(SHORT_URL, sortURL)
                        .addValue(LAST_REDIRECT_AT, Timestamp.from(Instant.now()));
        String finalQuery = String.format(selectQuery, schemaName);
        LOG.info("Query: {}", finalQuery);

        List<String> origURLs =
                        namedParameterJdbcTemplate.query(finalQuery, namedParameters, new RowMapper<String>() {
                            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                                return rs.getString(1);
                            };
                        });

        if (origURLs.isEmpty()) {
            return Optional.empty();
        } else {
            finalQuery = String.format(updateCountQuery, schemaName);
            LOG.info("Query: {}", finalQuery);

            namedParameterJdbcTemplate.update(finalQuery, namedParameters);
            return Optional.of(origURLs.get(0));
        }
    }

    @Override
    public List<ReportRow> getReport(ReportRequest reportRequest) {
        LOG.info("Parameters recieved reportRequest: {}", reportRequest);

        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                        .addValue(START_TIME, new Timestamp(reportRequest.getStart()))
                        .addValue(END_TIME, new Timestamp(reportRequest.getEnd()))
                        .addValue(OFFSET, reportRequest.getFrom()).addValue(LIMIT, reportRequest.getSize());

        String finalQuery = String.format(reportQuery, schemaName,
                        reportRequest.getSortOn() + " " + reportRequest.getSortOrder());
        LOG.info("Query: {}", finalQuery);

        return namedParameterJdbcTemplate.query(finalQuery, namedParameters, new ReportRowMapper());
    }
}
