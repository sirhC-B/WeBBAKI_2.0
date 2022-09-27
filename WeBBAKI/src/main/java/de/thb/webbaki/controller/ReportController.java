package de.thb.webbaki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ReportController {
    @GetMapping("report")
    public String showReport(){
      return "report";
    }

    /*@GetMapping("test")
    public void getPdf(HttpServletResponse response){
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            response.addHeader("Content-disposition", "attachment; filename=" + "employee.pdf");
            response.setContentType("application/pdf");
        }catch (Exception e){

        }
    }*/
}
