package de.thb.webbaki.service;

import de.thb.webbaki.controller.form.ReportFormModel;
import de.thb.webbaki.entity.Questionnaire;
import de.thb.webbaki.repository.QuestionnaireRepository;
import de.thb.webbaki.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Builder
public class QuestionnaireService {
    private final QuestionnaireRepository questionnaireRepository;
    private final UserRepository userRepository;

    public Questionnaire getEmptyQuestionnaire(){
        return new Questionnaire();
    }

    public Optional<Questionnaire> getQuestionnaire(long id){
        return questionnaireRepository.findById(id);
    }

    public List<Questionnaire> getAllQuestByUser(long id) {
        return questionnaireRepository.findAllByUser(userRepository.findById(id).get());
    }


    public void saveQuestionaire(ReportFormModel form){
        String[] prob = form.getProb();
        String[] imp = form.getImp();

        final var questionnaire = questionnaireRepository.save(Questionnaire.builder()
                .date(LocalDateTime.now())
                .user(form.getUser())
                ._1(prob[1]+","+imp[1])
                ._2(prob[2]+","+imp[2])
                ._3(prob[3]+","+imp[3])
                ._4(prob[4]+","+imp[4])
                ._5(prob[5]+","+imp[5])
                ._6(prob[6]+","+imp[6])
                ._7(prob[7]+","+imp[7])
                ._8(prob[8]+","+imp[8])
                ._9(prob[9]+","+imp[9])
                ._10(prob[10]+","+imp[10])
                ._11(prob[11]+","+imp[11])
                ._12(prob[12]+","+imp[12])
                ._13(prob[13]+","+imp[13])
                ._14(prob[14]+","+imp[14])
                ._15(prob[15]+","+imp[15])
                ._16(prob[16]+","+imp[16])
                ._17(prob[17]+","+imp[17])
                ._18(prob[18]+","+imp[18])
                ._19(prob[19]+","+imp[19])
                ._20(prob[20]+","+imp[20])
                ._21(prob[21]+","+imp[21])
                ._22(prob[22]+","+imp[22])
                ._23(prob[23]+","+imp[23])
                ._24(prob[24]+","+imp[24])
                ._25(prob[25]+","+imp[25])
                ._26(prob[26]+","+imp[26])
                ._27(prob[27]+","+imp[27])
                .comment(form.getComment())
                .build());
    };

    public String[] splitString(String str){
        String[] arrStr = str.split(",");

        return arrStr;
    };


}