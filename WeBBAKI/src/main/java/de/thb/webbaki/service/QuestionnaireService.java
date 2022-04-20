package de.thb.webbaki.service;

import de.thb.webbaki.controller.form.ReportFormModel;
import de.thb.webbaki.entity.Questionnaire;
import de.thb.webbaki.repository.QuestionnaireRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Builder
public class QuestionnaireService {
    private final QuestionnaireRepository questionnaireRepository;

    public Questionnaire getEmptyQuestionnaire(){
        return new Questionnaire();
    }

    public Optional<Questionnaire> getQuestionnaire(long id){
        return questionnaireRepository.findById(id);
    }

    /*
    TODO: Create QuestionnaireFormModel
    05. April: QuestionnaireFormModel Created, but needs further work
     */
    public void createRiskFromQuestionnaire(ReportFormModel form){
        final var risk = questionnaireRepository.save(Questionnaire.builder()
                .date(form.getDate())
                .comment(form.getComment()) 
                .szenarioType(form.getSzenarioType())
                .build());
    };
}
