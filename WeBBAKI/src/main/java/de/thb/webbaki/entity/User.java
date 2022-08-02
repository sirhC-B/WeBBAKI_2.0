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
@Getter
@Setter
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @ToString.Exclude
    private Set<Questionnaire> questionnaire;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users") //Fetching roles at the same time users get loaded
    private Collection<Role> roles = new ArrayList<>();

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    //Roles Getter
    public Collection<Role> getRoles(){
        return roles;
    }
    public void setRoles(Collection<Role>roles){
        this.roles = roles;
    }
}
