package org.innopolis.kuzymvas.secondexam.controller;

import org.innopolis.kuzymvas.secondexam.dao.ReportDAO;
import org.innopolis.kuzymvas.secondexam.dao.UserDAO;
import org.innopolis.kuzymvas.secondexam.service.ReportService;
import org.innopolis.kuzymvas.secondexam.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tables")
public class TableAccessController {

    private final Logger logger = LoggerFactory.getLogger(TableAccessController.class);
    private final UserService userService;
    private final ReportService reportService;

    @Autowired
    public TableAccessController(
            UserService userService, ReportService reportService) {
        this.userService = userService;
        this.reportService = reportService;
    }

    @GetMapping("/users")
    public List<UserDAO> getAllUsersRows() {
        logger.debug("New request for all users.");
        return userService.getAllRows();
    }

    @GetMapping("/users/{id}")
    public UserDAO getSingleUserRow(@PathVariable(value="id") final String id) {

        logger.debug("New request for user with id: " + id);
        int parsedID;
        try {
            parsedID = Integer.parseInt(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid id provided");
        }
        return userService.getSingleRow(parsedID);
    }

    @GetMapping("/reports")
    public List<ReportDAO> getAllReportsRows() {
        logger.debug("New request for all users.");
        return reportService.getAllRows();
    }

    @GetMapping("/reports/{id}")
    public ReportDAO getSingleReportRow(@PathVariable(value="id") final String id) {

        logger.debug("New request for user with id: " + id);
        int parsedID;
        try {
            parsedID = Integer.parseInt(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid id provided");
        }
        return reportService.getSingleRow(parsedID);
    }

}
