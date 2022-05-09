package de.thb.webbaki.repository;

import de.thb.webbaki.entity.Questionnaire;
import de.thb.webbaki.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = Questionnaire.class, idClass = Long.class)
public interface QuestionnaireRepository extends CrudRepository<Questionnaire, Long> {

    List<Questionnaire> findAllByUser(User user);
    Questionnaire findById(long id);
}
