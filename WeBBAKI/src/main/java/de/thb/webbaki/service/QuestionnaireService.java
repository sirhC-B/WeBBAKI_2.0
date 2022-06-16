package de.thb.webbaki.service;

import de.thb.webbaki.controller.form.ReportFormModel;
import de.thb.webbaki.entity.Questionnaire;
import de.thb.webbaki.repository.QuestionnaireRepository;
import de.thb.webbaki.repository.ScenarioRepository;
import de.thb.webbaki.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Builder
public class QuestionnaireService {
    private final QuestionnaireRepository questionnaireRepository;
    private final UserRepository userRepository;
    private final ScenarioRepository scenarioRepository;

    public Questionnaire getEmptyQuestionnaire(){
        return new Questionnaire();
    }

    public Questionnaire getQuestionnaire(long id){
        return questionnaireRepository.findById(id);
    }

    public Questionnaire getNewestQuestionnaireByUserId(long id){
        return questionnaireRepository.findFirstByUser_Id(id);
    }

    public List<Questionnaire> getAllQuestByUser(long id) {
        return questionnaireRepository.findAllByUser(userRepository.findById(id).get());
    }

    /*
    Delete Questionnaire by given ID
    Used Repository-Method deleteQuestionnaireById from
    @QuestionnaireRepository
     */
    public void delQuest(long id){
        questionnaireRepository.deleteQuestionnaireById(id);
    }


    public void saveQuestionaire(ReportFormModel form){
        String[] prob = form.getProb();
        String[] imp = form.getImp();

        Map<Long, String> map = new HashMap<>();
        for (long i = 1; i < prob.length; i++)
        {
            map.put(i, prob[(int)i] +';'+ imp[(int)i]);
        }

        final var questionnaire = questionnaireRepository.save(Questionnaire.builder()
                .date(LocalDateTime.now())
                .user(form.getUser())
                .mapping(map.toString())
                .comment(form.getComment())
                .build());
    };

    public Map<Long, String[]> getMapping(Questionnaire quest){
        String rawString = quest.getMapping();
        // CUT "{" & "}"
        rawString = rawString.substring(1, rawString.length() - 1);


        Map<Long, String[]> newMap = Arrays.stream(rawString.split(", "))
                .map(s -> s.split("="))
                .collect(Collectors.toMap(s -> Long.parseLong(s[0]), s -> s[1].split(";")));

        return newMap;
    }




}