package de.thb.webbaki.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity(name="privilege")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}
