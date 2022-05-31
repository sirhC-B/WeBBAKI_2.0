package de.thb.webbaki.service;

import de.thb.webbaki.controller.form.UserRegisterFormModel;
import de.thb.webbaki.entity.Role;
import de.thb.webbaki.entity.User;
import de.thb.webbaki.repository.RoleRepository;
import de.thb.webbaki.repository.UserRepository;
import de.thb.webbaki.service.Exceptions.UserAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.regex.Matcher;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Builder
@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class UserService {
    private UserRepository userRepository;      //initialize repository Object
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;    //Encoding Passwords for registered Users
    //Get a List of all Users using userRepository
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    //Getting a specific User by ID
    //Using Optional in case null is returned
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    //Getting a specific User by Email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /*
        Adding a new User with all attributes from User.java
        Parameter used: @Builder
     */
    public User addUser(String lastName, String firstName, String branche, String company,
                        String password, String email, Boolean enabled) {


        return userRepository.save(User.builder()
                .lastName(lastName)
                .firstName(firstName)
                .branche(branche)
                .company(company)
                .password(password)
                .email(email)
                .roles(Arrays.asList(roleRepository.findByName("KRITISBETREIBER")))
                .enabled(enabled)
                .build());
    }

    //Delete User by given Id via CRUD
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }


    //Checking if User already exists by email. Used in registerNewUser()
    public Boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    /*
        Registering new User with all parameters from User.java
        Using emailExists() to check whether user already exists
     */
    public void registerNewUser(UserRegisterFormModel form) throws UserAlreadyExistsException {
        if (emailExists(form.getEmail())){
            throw new UserAlreadyExistsException("Es existiert bereits ein Account mit folgender Email-Adresse: " + form.getEmail());
        } else
            userRepository.save(User.builder()
                    .lastName(form.getLastname())
                    .firstName(form.getFirstname())
                    .branche(form.getBranche())
                    .company(form.getCompany())
                    .password(passwordEncoder.encode(form.getPassword()))
                    .email(form.getEmail())
                    .enabled(true)
                    .build());
    }

    public void setCurrentLogin(User u) {
        u.setLastLogin(LocalDateTime.now());
        userRepository.save(u);
    }

    public void addRoleToUser(String email, String roleName){
        User user = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    public Role saveRole(Role role){
        return null;
    }



}
