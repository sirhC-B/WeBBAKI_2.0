package de.thb.webbaki.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String lastName;
    private String firstName;
    private String sector;
    private String company;

    // authentication
    private String password;
    private String email;
    private boolean enabled;
    private boolean tokenExpired;

    @ManyToOne
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))

    private Role roles;

}
