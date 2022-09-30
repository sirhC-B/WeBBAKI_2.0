package de.thb.webbaki.controller;

import de.thb.webbaki.service.ReportService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.thymeleaf.context.Context;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("report")
    public String showQuestionnaireForm(Model model) {
        model.addAttribute("test", "Testiii");
        return "report/report_container";
    }

    @GetMapping("report/download")
    public void downloadPdf(HttpServletResponse response){

        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=report.pdf";
        response.setHeader(headerKey, headerValue);

        Context context = new Context();
        context.setVariable("test", "Testiii");

        try {
            reportService.generatePdfFromHtml(reportService.parseThymeleafTemplateToHtml("report/report", context),
                    response.getOutputStream());
        } catch (IOException e) {
            //TODO add better error handling
            e.printStackTrace();
        }

    }

}