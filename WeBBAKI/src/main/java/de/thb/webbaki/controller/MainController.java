package de.thb.webbaki.controller;

import de.thb.webbaki.entity.User;
import de.thb.webbaki.security.MyUserDetails;
import de.thb.webbaki.security.MyUserDetailsService;
import de.thb.webbaki.security.authority.UserAuthority;
import de.thb.webbaki.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
public class MainController {
    private UserService userService;
    private MyUserDetailsService myUserDetailsService;

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

    @GetMapping("/setLoginTime")
    public void logintime(Model model,Authentication authentication) {
        UserDetails muser = myUserDetailsService.loadUserByUsername(authentication.getName());
        model.addAttribute("muser", muser);
        User user = userService.getUserByEmail(authentication.getName());
        userService.setCurrentLogin(user);
        model.addAttribute("user", user);

        }

}
