package de.thb.webbaki.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "questionnaire")
public class Questionnaire {
    @Id
    private long id;
    private Date date;
    private String comment;

    //Missing private SzenarioType szenariotype

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Questionnaire that = (Questionnaire) o;
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
