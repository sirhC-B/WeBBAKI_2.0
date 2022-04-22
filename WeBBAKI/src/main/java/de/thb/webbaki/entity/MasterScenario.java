package de.thb.webbaki.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name="master_scenario")
@Table
public class MasterScenario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;


    @OneToMany(mappedBy = "masterScenario")
    private Set<Scenario> scenarios;
}
