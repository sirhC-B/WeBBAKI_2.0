package de.thb.webbaki.controller;

import de.thb.webbaki.entity.Questionnaire;
import de.thb.webbaki.entity.User;
import de.thb.webbaki.service.*;
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

    @GetMapping("report/{reportBasis}")
    public String showQuestionnaireForm(@PathVariable("reportBasis") String reportBasis, Model model, Authentication authentication) throws Exception {
        final var masterScenarioList = masterScenarioService.getAllMasterScenarios();
        model.addAttribute("masterScenarioList",masterScenarioList);

        String username = authentication.getName();
        Queue<ThreatSituation> threatSituationQueue;

        if(reportBasis.equals("company") || reportBasis.equals("branche") || reportBasis.equals("national")) {
            final List<User> userList;
            if(reportBasis.equals("company")) {
                userList = userService.getUsersByCompany(userService.getUserByUsername(username).getCompany());
            }else if(reportBasis.equals("branche")){
                userList = userService.getUsersByBranche( userService.getUserByUsername(username).getBranche());
            }else{//for national
                userList = userService.getAllUsers();
            }

            List<Queue<ThreatSituation>> queueList = new LinkedList<Queue<ThreatSituation>>();
            for (User user : userList) {
                final Questionnaire newestQuestionaire = questionnaireService.getNewestQuestionnaireByUserId(user.getId());
                final Map<Long, String[]> questMap = questionnaireService.getMapping(newestQuestionaire);
                queueList.add(questionnaireService.getThreatSituationQueueFromMapping(questMap));
            }
            threatSituationQueue = questionnaireService.getThreatSituationAverageQueueFromQueues(queueList);
        }else if (reportBasis.equals("own")){
            final User user = userService.getUserByUsername(username);
            final Questionnaire newestQuestionaire = questionnaireService.getNewestQuestionnaireByUserId(user.getId());
            final Map<Long, String[]> questMap = questionnaireService.getMapping(newestQuestionaire);
            threatSituationQueue = questionnaireService.getThreatSituationQueueFromMapping(questMap);
        } else {
            //TODO bessere exception
            throw new Exception("Keine Richtige Adresse");
        }

        model.addAttribute("threatSituationQueue", threatSituationQueue);

        return "report/report_container";
    }

    @GetMapping("report/download")
    public void downloadPdf(HttpServletResponse response, Authentication authentication) throws IOException{

        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=report.pdf";
        response.setHeader(headerKey, headerValue);

        Context context = new Context();
        final var masterScenarioList = masterScenarioService.getAllMasterScenarios();
        context.setVariable("masterScenarioList",masterScenarioList);

        final User user = userService.getUserByUsername(authentication.getName());
        final Questionnaire newestQuestionaire = questionnaireService.getNewestQuestionnaireByUserId(user.getId());
        final Map<Long, String[]> questMap = questionnaireService.getMapping(newestQuestionaire);
        Queue<ThreatSituation> threatSituationQueue = questionnaireService.getThreatSituationQueueFromMapping(questMap);
        context.setVariable("threatSituationQueue", threatSituationQueue);

        reportService.generatePdfFromHtml(reportService.parseThymeleafTemplateToHtml("report/report", context),
                    response.getOutputStream());

    }

}