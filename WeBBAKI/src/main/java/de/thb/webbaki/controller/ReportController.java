package de.thb.webbaki.controller;

import de.thb.webbaki.controller.form.ReportFormModel;
import de.thb.webbaki.service.QuestionnaireService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class ReportController {

    private final QuestionnaireService questionnaireService;



    @GetMapping("/report")
    public String showQuestionnaireForm(Model model) {

        model.addAttribute("report", new ReportFormModel());
        return "report/create_report";
    }

}