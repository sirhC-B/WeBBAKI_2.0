package de.thb.webbaki.entity;

import de.thb.webbaki.enums.SzenarioType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name="scenario")
@Table
public class Scenario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @Column(length = 1024)
    @Size(max = 1024)
    private String description;



    @ManyToOne
    @JoinColumn(name = "master_scenario_id", nullable = false)
    private MasterScenario masterScenario;

}