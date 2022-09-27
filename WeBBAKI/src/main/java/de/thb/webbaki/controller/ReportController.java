package de.thb.webbaki.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReportController {


    @GetMapping("report")
    public String showQuestionnaireForm(Model model,Authentication authentication) {
        return "report";
    }


}