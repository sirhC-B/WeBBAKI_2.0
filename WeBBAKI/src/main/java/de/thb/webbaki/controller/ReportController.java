package de.thb.webbaki.controller;

import de.thb.webbaki.controller.form.ReportFormModel;
import de.thb.webbaki.entity.Questionnaire;
import de.thb.webbaki.service.MasterScenarioService;
import de.thb.webbaki.service.QuestionnaireService;
import de.thb.webbaki.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

@Controller
@AllArgsConstructor
public class ReportController {

    private final QuestionnaireService questionnaireService;
    private final MasterScenarioService masterScenarioService;
    private final UserService userService;



    @GetMapping("/report")
    public String showQuestionnaireForm(Model model,Authentication authentication) {

        model.addAttribute("report", new ReportFormModel());

        final var masterScenarioList = masterScenarioService.getAllMasterScenarios();
        model.addAttribute("masterScenarioList",masterScenarioList);

        userService.getUserByEmail(authentication.getName()).ifPresent(
                userService::setCurrentLogin
        );

        return "report/create_report";
    }
    @PostMapping("/report")
    public String submitQuestionnaire(@ModelAttribute("report") @Valid ReportFormModel questionnaireFormModel,
                                      BindingResult result, Authentication authentication,
                                      RedirectAttributes redirectAttributes) {

        userService.getUserByEmail(authentication.getName()).ifPresent(questionnaireFormModel::setUser);
        questionnaireService.saveQuestionaire(questionnaireFormModel);


        if (result.hasErrors()) {
            return "report/create_report";
        }
        redirectAttributes.addFlashAttribute("success", "Der Fragebogen wurde abgeschickt. \n" +
                "Unter 'Aktive Aufträge' können Sie einen passenden Provider auswählen");


        return "redirect:/home";
    }


    @GetMapping("/report/chronic")
    public String showQuestChronic(Authentication authentication,Model model) {


        userService.getUserByEmail(authentication.getName()).ifPresent(
                user -> {
                    final var questList = questionnaireService.getAllQuestByUser(user.getId());
                    model.addAttribute("questList", questList);
                }
        );

        return "report/chronic";
    }

    @GetMapping("/report/open/{questID}")
    public String showReportByID(@PathVariable("questID") long questID, Model model) {

        Questionnaire quest = questionnaireService.getQuestionnaire(questID);
        model.addAttribute("quest", quest);

        Map<Long, String[]> questMap = questionnaireService.getMapping(quest);
        model.addAttribute("questMap", questMap);

        // NEEDED
        model.addAttribute("report", new ReportFormModel());
        final var masterScenarioList = masterScenarioService.getAllMasterScenarios();
        model.addAttribute("masterScenarioList",masterScenarioList);

        return "report/show_report";
    }

}