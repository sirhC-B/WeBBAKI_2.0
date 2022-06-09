package de.thb.webbaki.controller;

import de.thb.webbaki.entity.Role;
import de.thb.webbaki.entity.User;
import de.thb.webbaki.service.RoleService;
import de.thb.webbaki.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@AllArgsConstructor
public class SuperAdminController implements Comparable {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("admin")
    public String showAllUsers(Model model) {
        final var users = userService.getAllUsers();
        users.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getLastName().compareTo(o2.getLastName());
            }
        });
        final var roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("users", users);

        return "permissions/admin";
    }

    @GetMapping("/permissions/addRoleToUser")
    public String addRoleToUser(Model model) {

        return "redirect:permissions/admin";
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
