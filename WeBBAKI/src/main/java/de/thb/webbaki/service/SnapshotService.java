package de.thb.webbaki.service;

import de.thb.webbaki.entity.Questionnaire;
import de.thb.webbaki.entity.User;
import de.thb.webbaki.repository.RoleRepository;
import de.thb.webbaki.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Builder
@AllArgsConstructor

public class SnapshotService {

    private final UserService userService;
    private final QuestionnaireService questionnaireService;

    public void createSnap(){

        List<User> userList = userService.getAllUsers();

        Map<String, Long[]> probMap = new HashMap<>();
        Map<String, Long[]> impMap = new HashMap<>();

        for (long i = 1; i < userList.size(); i++){
            long userID = userList.get((int)i).getId();

            Questionnaire quest = questionnaireService.getNewestQuestionnaireByUserId(userID);
            if(quest != null) {
                Map<Long, String[]> longMap = createMappingfromQuest(quest.getMapping());

                for (long j = 1; j < longMap.size(); j++) {
                    probMap.put(longMap.get(j)[0], new Long[]{userID, j});
                    impMap.put(longMap.get(j)[1], new Long[]{userID, j});
                }
            }
        }

        System.out.println(probMap.toString());

    }


    public Map<Long, String[]> createMappingfromQuest(String rawString){
        rawString = rawString.substring(1, rawString.length() - 1);

        return Arrays.stream(rawString.split(", "))
                .map(s -> s.split("="))
                .collect(Collectors.toMap(s -> Long.parseLong(s[0]), s -> s[1].split(";")));
    }
    
    
}
