package de.thb.webbaki.controller.form;

import de.thb.webbaki.entity.User;
import de.thb.webbaki.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportFormModel {



    @NotNull(message = "Probability null")
    private Probability probability;

    @NotNull(message = "Impact null")
    private Impact impact;

    @NotNull(message = "Risk null")
    private Risk risk;

    @NotNull(message = "SzenarioType not null")
    private String prob;

    @NotNull(message = "SzenarioType not null")
    private String imp;

    private User user;

}
