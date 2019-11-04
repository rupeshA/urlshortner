package com.app.urlshortner.service.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.app.urlshortner.model.ReportRequest;
import com.app.urlshortner.model.ReportRow;
import com.app.urlshortner.service.dao.ShortenerDAO.SortOrder;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortnerDAOImplTest {

    @Autowired
    private ShortenerDAO shortenerDAO;

    @Test
    @Transactional
    public void testSaveURLMapping() {
        int count = shortenerDAO.saveURLMapping("short", "testURL");
        assertEquals(1, count);
    }

    @Test
    @Transactional
    public void testSaveURLMapping_Negative_DuplicateInsert() {
        shortenerDAO.saveURLMapping("short", "testURL");
        int count = shortenerDAO.saveURLMapping("short", "testURL");
        assertEquals(0, count);
    }

    @Test
    public void testGetOrigURL_NonExistentEntry() {
        Optional<String> longURL = shortenerDAO.getOrigURL("short");
        assertEquals(Optional.empty(), longURL);
    }

    @Test
    @Transactional
    public void testGetOrigURL_ExistentEntry() {
        shortenerDAO.saveURLMapping("short", "testURL");
        Optional<String> longURL = shortenerDAO.getOrigURL("short");
        assertEquals("testURL", longURL.get());
    }

    @Test
    @Transactional
    public void testGetReport() {
        shortenerDAO.saveURLMapping("short", "testURL");
        shortenerDAO.getOrigURL("short");
        ReportRequest rr = new ReportRequest(0l, 9999999999999l, 0, 999, "created_at", "asc");
        List<ReportRow> list = shortenerDAO.getReport(rr);
        assertNotNull(list);
        assertTrue(1 == list.size());
        ReportRow reportRow = list.get(0);
        assertEquals("short", reportRow.getShortURL());
        assertEquals(1, reportRow.getRedirectCount());
        assertEquals("testURL", reportRow.getLongURL());
        shortenerDAO.getOrigURL("short");
        shortenerDAO.getOrigURL("short");
        list = shortenerDAO.getReport(rr);
        reportRow = list.get(0);
        assertEquals(3, reportRow.getRedirectCount());
    }
}
