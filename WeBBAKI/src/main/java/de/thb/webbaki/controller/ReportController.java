package de.thb.webbaki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {
    @GetMapping("report")
    public String showReport(){
      return "report";
    }
}
