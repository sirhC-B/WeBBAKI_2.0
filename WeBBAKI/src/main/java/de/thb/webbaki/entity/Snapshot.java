package de.thb.webbaki.entity;

import lombok.*;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name="snapshot")
@Table
public class Snapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String questionaireIDs;

    private LocalDateTime date;


}
