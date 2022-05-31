package de.thb.webbaki.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
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

    // authentication
    private String password;
    private String email;
    private boolean enabled;
    private boolean tokenExpired;
    private LocalDateTime lastLogin;

    @OneToMany(mappedBy = "user")
    private Set<Questionnaire> questionnaire;

    @ManyToMany(fetch = FetchType.EAGER) //Fetching roles at the same time users get loaded
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles = new ArrayList<>();


    //Roles Getter
    public Collection<Role> getRoles(){
        return roles;
    }
    public void setRoles(Collection<Role>roles){
        this.roles = roles;
    }
}
