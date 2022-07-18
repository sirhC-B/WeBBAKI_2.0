package de.thb.webbaki.mail.confirmation;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@RepositoryDefinition(domainClass = ConfirmationToken.class, idClass = Long.class)
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);


    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.token = ?1")
    int setConfirmedAt(String token, LocalDateTime ConfirmedAt);
}
