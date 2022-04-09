package de.thb.webbaki.controller.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterFormModel {

    @NotNull(message = "LastName not null")
    @NotEmpty(message = "Nachname darf nicht leer sein")
    private String lastName;

    @NotNull(message = "firstName not null")
    @NotEmpty(message = "Vorname darf nicht leer sein")
    private String firstName;

    @NotNull(message = "sector not null")
    @NotEmpty(message = "Sektor darf nicht leer sein")
    private String sector;

    @NotNull(message = "company not null")
    @NotEmpty(message = "Company darf nicht leer sein")
    private String company;

    @NotNull(message = "password not null")
    @NotEmpty(message = "Passwort darf nicht leer sein")
    private String password;
    private String confirmPassword;

    @NotNull(message = "email not null")
    @NotEmpty(message = "Email darf nicht leer sein")
    private String email;

    @NotNull(message = "enabled not null")
    @NotEmpty(message = "Enabled darf nicht leer sein")
    private boolean enabled;

}
