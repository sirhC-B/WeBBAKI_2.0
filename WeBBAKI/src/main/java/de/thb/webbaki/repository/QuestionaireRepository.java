package de.thb.webbaki.repository;

import de.thb.webbaki.entity.Questionnaire;

import java.util.Optional;

public interface QuestionaireRepository {

    public Optional<Questionnaire> findById(long id);
}
