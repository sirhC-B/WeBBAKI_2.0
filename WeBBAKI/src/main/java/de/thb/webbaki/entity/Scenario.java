package de.thb.webbaki.entity;

import de.thb.webbaki.enums.SzenarioType;
import lombok.*;

import javax.persistence.*;

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
    private SzenarioType szenarioType;

}