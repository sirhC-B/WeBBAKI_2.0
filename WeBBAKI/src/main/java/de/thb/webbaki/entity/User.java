package de.thb.webbaki.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String lastName;
    private String firstName;
    private String sector;
    private String branche;
    private String company;
    private boolean tokenExpired = false;
    private LocalDateTime lastLogin;
    private boolean enabled = false;
    private boolean enabledByUser = false;

    // authentication
    private String password;
    private String email;
    private String username;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private Set<Questionnaire> questionnaire;

    @ManyToMany(fetch = FetchType.EAGER) //Fetching roles at the same time users get loaded
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;


    //Roles Getter
    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

}
