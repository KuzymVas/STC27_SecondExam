package org.innopolis.kuzymvas.secondexam.service;

import org.innopolis.kuzymvas.secondexam.dao.ReportDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReportService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReportDAO> getAllRows() {
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM reports");
        final List<ReportDAO> daos = new ArrayList<>();
        rows.stream()
                .map(this::parseRow)
                .collect(Collectors.toCollection(() -> daos));
        return daos;
    }

    public ReportDAO getSingleRow(Integer id) {
        final Map<String, Object> row = DataAccessUtils.singleResult(
                jdbcTemplate.queryForList("SELECT * FROM reports WHERE id = ?", id));
        if (row == null) {
            throw new IllegalArgumentException("Report with given id do not exist");
        }
        return parseRow(row);
    }

    private ReportDAO parseRow(Map<String, Object> row) {
        final ReportDAO dao = new ReportDAO();
        dao.setId((Integer) row.get("id"));
        dao.setName((String) row.get("name"));
        dao.setSubmissionDate((Date) row.get("submission_date"));
        dao.setText((String) row.get("text"));
        dao.setSubmittedBy((Integer) row.get("submitted_by"));
        dao.setApproved((Boolean) row.get("approved"));
        return dao;
    }
}
