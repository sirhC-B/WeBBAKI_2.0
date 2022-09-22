package de.thb.webbaki.controller;

import de.thb.webbaki.controller.form.ReportFormModel;
import de.thb.webbaki.entity.Questionnaire;
import de.thb.webbaki.entity.User;
import de.thb.webbaki.entity.Scenario;
import de.thb.webbaki.service.MasterScenarioService;
import de.thb.webbaki.service.QuestionnaireService;
import de.thb.webbaki.service.ScenarioService;
import de.thb.webbaki.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Map;

@Controller
@AllArgsConstructor
public class ReportController {

    private final QuestionnaireService questionnaireService;
    private final MasterScenarioService masterScenarioService;
    private final ScenarioService scenarioService;
    private final UserService userService;



    @GetMapping("/report")
    public String showQuestionnaireForm(Model model,Authentication authentication) {

        model.addAttribute("report", new ReportFormModel());

        final var masterScenarioList = masterScenarioService.getAllMasterScenarios();
        model.addAttribute("masterScenarioList",masterScenarioList);

        Questionnaire quest = questionnaireService.getNewestQuestionnaireByUserId(userService.getUserByEmail(authentication.getName()).getId());
        model.addAttribute("quest", quest);

        Map<Long, String[]> questMap = questionnaireService.getMapping(quest);
        model.addAttribute("questMap", questMap);

        return "report/create_report";
    }
    @PostMapping("/report")
    public String submitQuestionnaire(@ModelAttribute("report") @Valid ReportFormModel questionnaireFormModel,
                                      Authentication authentication) {

        if (userService.getUserByEmail(authentication.getName()) != null){
            User user = userService.getUserByEmail(authentication.getName());
            questionnaireFormModel.setUser(user);
            questionnaireService.saveQuestionaire(questionnaireFormModel);
        }

        return "redirect:/report/chronic";
    }


    @GetMapping("/report/chronic")
    public String showQuestChronic(Authentication authentication,Model model) {

        if (userService.getUserByEmail(authentication.getName()) != null) {
            User user = userService.getUserByEmail(authentication.getName());
            final var questList = questionnaireService.getAllQuestByUser(user.getId());
            model.addAttribute("questList", questList);

        }
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

        return "report/create_report";
    }


    @Transactional
    @GetMapping(path = "/report/chronic/{questID}")
    public String deleteQuestionnaireByID(@PathVariable("questID") long questID){
        questionnaireService.delQuest(questID);

        return "redirect:/report/chronic";
    }

    @GetMapping("/report/add_szenario")
    public String addSzenario(Model model){
        final var masterScenarioList = masterScenarioService.getAllMasterScenarios();
        model.addAttribute("masterScenarioList",masterScenarioList);

        Scenario scenario = new Scenario();
        model.addAttribute("scenario",scenario);

        return "report/add_szenario";
    }

    @PostMapping("/report/add_szenario")
    public String submitSzenario(@ModelAttribute("scenario") Scenario scenario){
        String[] sList = scenario.getName().split(",");
        scenario.setName(sList[0]);
        scenario.setMasterScenario(masterScenarioService.getAllMasterScenarios()
                .get(Integer.parseInt(sList[1])));
        scenarioService.addScenario(scenario);


        return "redirect:/report";
    }


}