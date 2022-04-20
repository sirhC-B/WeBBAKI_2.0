package de.thb.webbaki.repository;

import de.thb.webbaki.entity.Scenario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = Scenario.class, idClass = Long.class)
public interface ScenarioRepository extends CrudRepository<Scenario, Long> {

    Optional<Scenario> findById(long id);

    Optional<Scenario> findByName(String name);

    Optional<Scenario> findAllByMasterScenario_Id(long id);






}