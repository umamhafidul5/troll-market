package com.indocyber.controller.mvc;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.indocyber.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private HttpSession session;

//    @RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
//    public ModelAndView home() {
//        ModelAndView model = new ModelAndView();
//
//        model.setViewName("home");
//        return model;
//    }

    @RequestMapping(value = "/history-pdf", method = RequestMethod.GET)
    public void export(ModelAndView model, HttpServletResponse response) throws IOException, JRException, SQLException {
        JasperPrint jasperPrint = null;

        String seller = session.getAttribute("seller").toString();
        String buyer = session.getAttribute("buyer").toString();
        String fileName = "history_transaction_"+Timestamp.valueOf(LocalDateTime.now())+".pdf";

        if (!seller.equals("") && !buyer.equals("")) {
            fileName = seller+"_"+buyer+"_"+fileName;
        } else if (!seller.equals("")) {
            fileName = seller+"_"+fileName;
        } else if (!buyer.equals("")) {
            fileName = buyer+"_"+fileName;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", String.format("inline; filename=%s", fileName));


        OutputStream out = response.getOutputStream();
        jasperPrint = reportService.exportPdfFile(seller, buyer);
        JasperExportManager.exportReportToPdfStream(jasperPrint, out);
//        out.flush();
//        out.close();
    }
}
