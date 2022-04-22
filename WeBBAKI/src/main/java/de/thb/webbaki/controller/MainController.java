package de.thb.webbaki.controller;

import de.thb.webbaki.security.authority.UserAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("account")
    public String securedAccountPage() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString();

        if (role.contains(UserAuthority.USER)) {
            return "account/account_user";
       // } else if (role.contains(ProviderAuthority.PROVIDER)) {
        //    return "account/account_provider";
        } else return "home";
    }
}
