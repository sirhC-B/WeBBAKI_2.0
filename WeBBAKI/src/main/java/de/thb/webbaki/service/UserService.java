package de.thb.webbaki.service;

import de.thb.webbaki.controller.form.UserRegisterFormModel;
import de.thb.webbaki.controller.form.UserToRoleFormModel;
import de.thb.webbaki.entity.Role;
import de.thb.webbaki.entity.User;
import de.thb.webbaki.mail.EmailSender;
import de.thb.webbaki.mail.confirmation.ConfirmationToken;
import de.thb.webbaki.mail.confirmation.ConfirmationTokenService;
import de.thb.webbaki.repository.RoleRepository;
import de.thb.webbaki.repository.UserRepository;
import de.thb.webbaki.service.Exceptions.UserAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class UserService {
    private UserRepository userRepository; ////initialize repository Object
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private ConfirmationTokenService confirmationTokenService;
    private EmailSender emailSender;

    //Repo Methods --------------------------
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    /**
     * Check if email is already in use and existing in DB
     *
     * @param email to check
     * @return boolean
     */
    public Boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public Boolean usernameExists(String username){return userRepository.findByUsername(username) != null;}

    /**
     * @param user is used to create new user -> forwarded to registerNewUser
     * @return newly created token
     */
    public String createToken(User user) {

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token, LocalDateTime.now(), LocalDateTime.now().plusDays(3), user);

        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    /**
     * Registering new User with all parameters from User.java
     * Using emailExists() to check whether user already exists
     */
    public void registerNewUser(final UserRegisterFormModel form) throws UserAlreadyExistsException {
        if (usernameExists(form.getUsername())) {
            throw new UserAlreadyExistsException("Es existiert bereits ein Account mit folgender Email-Adresse: " + form.getEmail());
        } else {

            final User user = new User();

            user.setLastName(form.getLastname());
            user.setFirstName(form.getFirstname());
            user.setBranche(form.getBranche());
            user.setCompany(form.getCompany());
            user.setPassword(passwordEncoder.encode(form.getPassword()));
            user.setEmail(form.getEmail());
            user.setRoles(Arrays.asList(roleRepository.findByName("DEFAULT_USER")));
            user.setUsername(form.getUsername());
            user.setEnabled(false);

            userRepository.save(user);

            String token = createToken(user); // To create the token of the user


            String userLink = "http://localhost:8080/confirmation/confirmByUser?token=" + token;
            String adminLink = "http://localhost:8080/confirmation/confirm?token=" + token;

            //Email to Superadmin
            emailSender.send("schrammbox@proton.me", buildEmail("Christian", adminLink));

            //Email to new registered user
            emailSender.send(form.getEmail(), buildEmail(form.getFirstname(), userLink));


        }
    }

    @Transactional
    public String confirmToken(String token) throws IllegalStateException {
        ConfirmationToken confirmationToken = confirmationTokenService.getConfirmationToken(token);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed.");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        } else

            confirmationTokenService.setConfirmedAt(token);
        enableUser(confirmationToken.getUser().getEmail(), token);

        return "/confirmation/confirm";
    }

    public void setCurrentLogin(User u) {
        u.setLastLogin(LocalDateTime.now());
        userRepository.save(u);
    }

    /**
     * USED IN SUPERADMIN DASHBOARD
     * Superadmin can add Roles to specific Users
     *
     * @param formModel to get Userdata, especially roles from user
     */
    public void addRoleToUser(final UserToRoleFormModel formModel) {
        String[] roles = formModel.getRole();
        for (int i = 1; i < roles.length; i++) {
            if (!Objects.equals(roles[i], "none") && !Objects.equals(roles[i], null)) {
                User user = userRepository.findById(i).get();
                if (!user.getRoles().contains(roleRepository.findByName(roles[i]))) {
                    user.addRole((roleRepository.findByName(roles[i])));
                }
            }
        }
    }

    public void removeRoleFromUser(final UserToRoleFormModel formModel) {
        String[] roleDel = formModel.getRoleDel();
        for (int i = 1; i < roleDel.length; i++) {
            if (!Objects.equals(roleDel[i], "none") && !Objects.equals(roleDel[i], null)) {
                User user = userRepository.findById(i).get();
                Role r = roleRepository.findByName(roleDel[i]);
                user.removeRole(r);


            }
        }
    }

    /**
     * Using USERDETAILS -> Enabling User in Spring security
     * User is enabled if user_confirmation && admin_confirmation == TRUE
     *
     * @param email to get the user
     * @param token to get the according token
     * @return value TRUE or FALSE based on INTEGER value (0 = false, 1 = true)
     */
    public int enableUser(String email, String token) {

        if (confirmationTokenService.accessGranted(token)) {
            return userRepository.enableUser(email);
        } else return 0;
    }

    /**
     * Setting user_confirmation TRUE or False
     *
     * @param token to get matching ConfirmationToken
     * @return value TRUE or FALSE based on bit value (0 = false, 1 = true)
     */
    public int userConfirmation(String token) {
        return confirmationTokenService.setConfirmedByUser(token);
    }

    /**
     * Setting admin_confirmation TRUE or False
     *
     * @param token to get matching ConfirmationToken
     * @return value TRUE or FALSE based on bit value (0 = false, 1 = true)
     */
    public int adminConfirmation(String token) {
        return confirmationTokenService.setConfirmedByAdmin(token);
    }

    /**
     * Setting user_confirmation TRUE or False and getting HTML Page with confirmation Details
     * using method public int userConfirmation(String token)
     *
     * @param token to get matching ConfirmationToken
     * @return value TRUE or FALSE based on bit value (0 = false, 1 = true)
     */
    public String confirmUser(String token) {
        userConfirmation(token);
        return "confirmation/confirmedByUser";
    }

    /**
     * Setting admin_confirmation TRUE or False and getting HTML Page with confirmation Details
     * using method public int adminConfirmation(String token)
     *
     * @param token to get matching ConfirmationToken
     * @return value TRUE or FALSE based on bit value (0 = false, 1 = true)
     */
    public String confirmAdmin(String token) {
        adminConfirmation(token);
        return "confirmation/confirm";
    }


    // ------------------------------MAILS TO USER AND ADMIN --------------------------------------------------------

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Bestätigung eines neuen WebBaKI-Nutzers</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Es hat sich ein neuer WebBaKI-Nutzer registriert. Der Account kann unter folgendem Link aktiviert werden: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Nutzer verifizieren</a> </p></blockquote>\n Der Link bleibt 3 Tage gültig. " +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}
