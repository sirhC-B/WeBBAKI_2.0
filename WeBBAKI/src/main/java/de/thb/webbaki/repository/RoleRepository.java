package de.thb.webbaki.repository;

import de.thb.webbaki.entity.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@RepositoryDefinition(domainClass = Role.class, idClass = Long.class)
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);
    Optional<Role> findById(Long id);

    @Override
    void delete(Role entity);
}
