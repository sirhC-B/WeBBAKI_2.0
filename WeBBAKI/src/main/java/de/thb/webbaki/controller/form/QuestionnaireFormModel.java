package de.thb.webbaki.controller.form;

import enums.SzenarioType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionnaireFormModel {

    @NotNull(message = "Date not null")
    @NotEmpty(message = "Datum leer")
    private LocalDate date;

    @NotNull(message = "comment not null")
    @NotEmpty(message = "Branchenkommentierung leer")
    private String comment;

    @NotNull(message = "SzenarioType not null")
    private SzenarioType szenarioType;

}
