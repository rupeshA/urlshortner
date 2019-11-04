CREATE SCHEMA IF NOT EXISTS TEST ; 
SET SCHEMA TEST ;
--DB_CLOSE_DELAY=-1;
CREATE TABLE IF NOT EXISTS test.url_mapping
(
    short_url varchar,
    orig_url varchar,
    created_at timestamp,
    last_resolved_at timestamp,
    resolved_count INTEGER default 0,
    CONSTRAINT url_mapping_pk UNIQUE (short_url)
);