package de.thb.webbaki.service;

import de.thb.webbaki.repository.QuestionaireRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Builder
public class QuestionnaireService {
    private final QuestionaireRepository questionaireRepository;
}
