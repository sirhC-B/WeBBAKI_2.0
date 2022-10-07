package de.thb.webbaki.controller;

import de.thb.webbaki.entity.Questionnaire;
import de.thb.webbaki.entity.User;
import de.thb.webbaki.enums.ReportFocus;
import de.thb.webbaki.service.*;
import de.thb.webbaki.service.Exceptions.WrongPathException;
import de.thb.webbaki.service.helper.ThreatSituation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ReportController {

    @Autowired
    private ReportService reportService;
    @Autowired
    private MasterScenarioService masterScenarioService;
    @Autowired
    private QuestionnaireService questionnaireService;
    @Autowired
    private UserService userService;

    @GetMapping("report/{reportFocus}")
    public String showQuestionnaireForm(@PathVariable("reportFocus") String reportFocusString, Model model, Authentication authentication) throws WrongPathException{
        final var masterScenarioList = masterScenarioService.getAllMasterScenarios();
        model.addAttribute("masterScenarioList",masterScenarioList);
        ReportFocus reportFocus = ReportFocus.getReportFocusByEnglishRepresentation(reportFocusString);
        model.addAttribute("reportFocus", reportFocus);

        Queue<ThreatSituation> threatSituationQueue = reportService.getThreatSituationQueueByReportFocus(reportFocus, authentication.getName());

        model.addAttribute("threatSituationQueue", threatSituationQueue);

        return "report/report_container";
    }

    @GetMapping("report/{reportFocus}/download")
    public void downloadPdf(@PathVariable("reportFocus") String reportFocusString, HttpServletResponse response, Authentication authentication) throws WrongPathException, IOException{

        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=report.pdf";
        response.setHeader(headerKey, headerValue);

        Context context = new Context();
        final var masterScenarioList = masterScenarioService.getAllMasterScenarios();
        context.setVariable("masterScenarioList",masterScenarioList);
        ReportFocus reportFocus = ReportFocus.getReportFocusByEnglishRepresentation(reportFocusString);
        context.setVariable("reportFocus", reportFocus);

        Queue<ThreatSituation> threatSituationQueue = reportService.getThreatSituationQueueByReportFocus(reportFocus, authentication.getName());

        context.setVariable("threatSituationQueue", threatSituationQueue);

        reportService.generatePdfFromHtml(reportService.parseThymeleafTemplateToHtml("report/report", context),
                    response.getOutputStream());

    }

}