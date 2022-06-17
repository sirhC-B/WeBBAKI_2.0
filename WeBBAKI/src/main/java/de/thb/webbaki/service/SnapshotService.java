package de.thb.webbaki.service;

import de.thb.webbaki.entity.Questionnaire;
import de.thb.webbaki.entity.Snapshot;
import de.thb.webbaki.entity.User;
import de.thb.webbaki.repository.RoleRepository;
import de.thb.webbaki.repository.SnapshotRepository;
import de.thb.webbaki.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Builder
@AllArgsConstructor

public class SnapshotService {

    private final UserService userService;
    private final QuestionnaireService questionnaireService;
    private final SnapshotRepository snapshotRepository;

    public void createSnap(){

        List<User> userList = userService.getAllUsers();
        List<Long> questIDs = new ArrayList<>();

        for (long i = 1; i < userList.size(); i++){
            long userID = userList.get((int)i).getId();


            Questionnaire quest = questionnaireService.getNewestQuestionnaireByUserId(userID);
            if(quest != null) {
                questIDs.add(quest.getId());
            }
        }

        /* Perist Snapshot */

        final Snapshot snap = new Snapshot();
        snap.setDate(LocalDate.now());
        snap.setQuestionaireIDs(questIDs.toString());
        snapshotRepository.save(snap);



    }
    
    
}
