package com.indocyber.service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.IOException;
import java.sql.SQLException;

public interface ReportService {
    JasperPrint exportPdfFile(String seller, String buyer) throws SQLException, JRException, IOException;
}
