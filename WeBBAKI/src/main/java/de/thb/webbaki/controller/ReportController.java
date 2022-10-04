package de.thb.webbaki.controller;

import de.thb.webbaki.entity.Questionnaire;
import de.thb.webbaki.entity.User;
import de.thb.webbaki.service.MasterScenarioService;
import de.thb.webbaki.service.QuestionnaireService;
import de.thb.webbaki.service.ReportService;
import de.thb.webbaki.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @GetMapping("report")
    public String showQuestionnaireForm(Model model, Authentication authentication) {
        final var masterScenarioList = masterScenarioService.getAllMasterScenarios();
        model.addAttribute("masterScenarioList",masterScenarioList);


        //TODO abfangen fehler, wenn es kein Questionary gibt?
        final User user = userService.getUserByUsername(authentication.getName());
        final Questionnaire newestQuestionaire = questionnaireService.getNewestQuestionnaireByUserId(user.getId());
        final Map<Long, String[]> questMap = questionnaireService.getMapping(newestQuestionaire);
        Queue<Long> threatSituationQueue = questionnaireService.getThreatSituationQueueFromMapping(questMap);
        model.addAttribute("threatSituationQueue", threatSituationQueue);

        return "report/report_container";
    }

    @GetMapping("report/download")
    public void downloadPdf(HttpServletResponse response, Authentication authentication){

        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=report.pdf";
        response.setHeader(headerKey, headerValue);

        Context context = new Context();
        final var masterScenarioList = masterScenarioService.getAllMasterScenarios();
        context.setVariable("masterScenarioList",masterScenarioList);

        //TODO abfangen fehler, wenn es kein Questionary gibt?
        final User user = userService.getUserByUsername(authentication.getName());
        final Questionnaire newestQuestionaire = questionnaireService.getNewestQuestionnaireByUserId(user.getId());
        final Map<Long, String[]> questMap = questionnaireService.getMapping(newestQuestionaire);
        Queue<Long> threatSituationQueue = questionnaireService.getThreatSituationQueueFromMapping(questMap);
        context.setVariable("threatSituationQueue", threatSituationQueue);
        try {
            reportService.generatePdfFromHtml(reportService.parseThymeleafTemplateToHtml("report/report", context),
                    response.getOutputStream());
        } catch (IOException e) {
            //TODO add better error handling
            e.printStackTrace();
        }

    }

}