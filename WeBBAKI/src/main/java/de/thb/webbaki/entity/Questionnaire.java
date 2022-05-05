package de.thb.webbaki.entity;

import de.thb.webbaki.enums.SzenarioType;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity(name = "questionnaire")
@Table
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(length = 1000)
    @Size(max = 1000)
    private String comment;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    // Szenarios sortet nach ID in DB
    private String _1,_2,_3,_4,_5,_6,_7,_8,_9,_10,_11,_12,_13,
            _14,_15,_16,_17,_18,_19,_20,_21,_22,_23,_24,_25,_26,_27;


    public String[] getCol(int id){
        switch(id) {
            case 1:
                return _1.split(",");
            case 2:
                return _2.split(",");
            case 3:
                return _3.split(",");
            case 4:
                return _4.split(",");
            case 5:
                return _5.split(",");
            case 6:
                return _6.split(",");
            case 7:
                return _7.split(",");
            case 8:
                return _8.split(",");
            case 9:
                return _9.split(",");
            case 10:
                return _10.split(",");
            case 11:
                return _11.split(",");
            case 12:
                return _12.split(",");
            case 13:
                return _13.split(",");
            case 14:
                return _14.split(",");
            case 15:
                return _15.split(",");
            case 16:
                return _16.split(",");
            case 17:
                return _17.split(",");
            case 18:
                return _18.split(",");
            case 19:
                return _19.split(",");
            case 20:
                return _20.split(",");
            case 21:
                return _21.split(",");
            case 22:
                return _22.split(",");
            case 23:
                return _23.split(",");
            case 24:
                return _24.split(",");
            case 25:
                return _25.split(",");
            case 26:
                return _26.split(",");
            case 27:
                return _27.split(",");
            default:
                return null;
        }
    }










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
