package com.indocyber.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public JasperPrint exportPdfFile(String seller, String buyer) throws SQLException, JRException, IOException {
        Connection conn = jdbcTemplate.getDataSource().getConnection();

        String path = resourceLoader.getResource("classpath:reports/troll-market-history.jrxml").getURI().getPath();

        JasperReport jasperReport = JasperCompileManager.compileReport(path);

        // Parameters for report
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("Seller", "%"+seller+"%");
        parameters.put("Buyer", "%"+buyer+"%");
        parameters.put("Cherry", "reports/cherry.jpg");

        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);

        return print;
    }
}
