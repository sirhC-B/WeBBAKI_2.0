package de.thb.webbaki.repository;


import de.thb.webbaki.entity.Privilege;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = Privilege.class, idClass = Long.class)
public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {

    Optional<Privilege> findByName(String email);
}
