package de.thb.webbaki.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @OneToMany(mappedBy = "roles")
    private Collection<User> users;


    @ManyToMany
    @JoinTable(
        name = "roles_privileges",
        joinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(
          name = "privilege_id", referencedColumnName = "id"))

    private Collection<Privilege> privileges;

    public Role(String name) {
        this.name = name;
    }
}

//https://www.baeldung.com/role-and-privilege-for-spring-security-registration