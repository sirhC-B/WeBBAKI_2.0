package de.thb.webbaki.service;

import de.thb.webbaki.controller.form.ReportFormModel;
import de.thb.webbaki.entity.Questionnaire;
import de.thb.webbaki.repository.QuestionnaireRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public void saveQuestionaire(ReportFormModel form){
        String[] prob = splitString(form.getProb());
        String[] imp = splitString(form.getImp());

        final var questionnaire = questionnaireRepository.save(Questionnaire.builder()
                .date(LocalDateTime.now())
                .user(form.getUser())
                ._1(prob[0]+","+imp[0])
                ._2(prob[1]+","+imp[1])
                ._3(prob[2]+","+imp[2])
                ._4(prob[3]+","+imp[3])
                ._5(prob[4]+","+imp[4])
                ._6(prob[5]+","+imp[5])
                ._7(prob[6]+","+imp[6])
                ._8(prob[7]+","+imp[7])
                ._9(prob[8]+","+imp[8])
                ._10(prob[9]+","+imp[9])
                ._11(prob[10]+","+imp[10])
                ._12(prob[11]+","+imp[11])
                ._13(prob[12]+","+imp[12])
                ._14(prob[13]+","+imp[13])
                ._15(prob[14]+","+imp[14])
                ._16(prob[15]+","+imp[15])
                ._17(prob[16]+","+imp[16])
                ._18(prob[17]+","+imp[17])
                ._19(prob[18]+","+imp[18])
                ._20(prob[19]+","+imp[19])
                ._21(prob[20]+","+imp[20])
                ._22(prob[21]+","+imp[21])
                ._23(prob[22]+","+imp[22])
                ._24(prob[23]+","+imp[23])
                ._25(prob[24]+","+imp[24])
                ._26(prob[25]+","+imp[25])
                ._27(prob[26]+","+imp[26])
                .build());
    };

    public String[] splitString(String str){
        String[] arrStr = str.split(",");

        return arrStr;
    };


}
