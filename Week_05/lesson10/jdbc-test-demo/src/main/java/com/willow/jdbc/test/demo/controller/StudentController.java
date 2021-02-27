package com.willow.jdbc.test.demo.controller;

import com.willow.jdbc.test.demo.jdbc.HikariProcess;
import com.willow.jdbc.test.demo.jdbc.PrepareStatementProcess;
import com.willow.jdbc.test.demo.jdbc.StatementProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class StudentController {
    @Autowired
    private StatementProcess statementProcess;
    @Autowired
    private PrepareStatementProcess prepareStatementProcess;
    @Autowired
    private HikariProcess hikariProcess;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) throws SQLException, ClassNotFoundException {
        statementProcess.doProcess();
        return String.format("Hello %s!", name);
    }

    @GetMapping("/hello2")
    public String hello2(@RequestParam(value = "name", defaultValue = "World") String name) throws SQLException, ClassNotFoundException {
        prepareStatementProcess.doProcess();
        prepareStatementProcess.doProcessBatch();
        return String.format("Hello %s!", name);
    }

    @GetMapping("/hello3")
    public String hello3(@RequestParam(value = "name", defaultValue = "World") String name) throws SQLException, ClassNotFoundException {
        hikariProcess.executeInsert();
        return String.format("Hello %s!", name);
    }

}
