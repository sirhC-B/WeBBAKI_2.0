package de.thb.webbaki.entity;

import enums.SzenarioType;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

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
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(length = 1000)
    @Size(max = 1000)
    private String comment;

    @Enumerated(EnumType.ORDINAL)
    private SzenarioType szenarioType;
    private LocalDate date;

    //Question: Strings for SubTopics?


    //EQUALS & HASHCODE
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
