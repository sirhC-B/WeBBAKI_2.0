package de.thb.webbaki.mail.confirmation;

import de.thb.webbaki.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ConfirmationToken {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @Column(nullable = false)
    private String token;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User user;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, User user){
        setToken(token);
        setCreatedAt(createdAt);
        setExpiresAt(expiresAt);
        setUser(user);
    }
}
