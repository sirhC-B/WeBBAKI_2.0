package de.thb.webbaki.repository;

import de.thb.webbaki.entity.Snapshot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Snapshot.class, idClass = Long.class)
public interface SnapshotRepository extends CrudRepository<Snapshot, Long> {




}
