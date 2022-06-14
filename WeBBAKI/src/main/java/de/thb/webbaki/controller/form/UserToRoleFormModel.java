package de.thb.webbaki.controller.form;

import de.thb.webbaki.entity.Role;
import de.thb.webbaki.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserToRoleFormModel {

    //Get Role for User
    private String[] role;

    //Get User for Role
    private String[] user;
}
