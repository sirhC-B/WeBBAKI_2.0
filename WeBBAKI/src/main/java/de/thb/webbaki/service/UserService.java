package de.thb.webbaki.service;

import de.thb.webbaki.entity.User;
import de.thb.webbaki.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.Optional;


@Builder
@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;      //initialize repository Object
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
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /*
        Adding a new User with all attributes from User.java
        Parameter used: @Builder
     */
    public User addUser(String lastName, String firstName, String sector, String company,
                        String password, String email, Boolean enabled) {
        return userRepository.save(User.builder()
                .lastName(lastName)
                .firstName(firstName)
                .sector(sector)
                .company(company)
                .password(password)
                .email(email)
                .enabled(enabled)
                .build());
    }

    //Delete User by given Id via CRUD
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }


    //Checking if User already exists by email. Used in registerNewUser()
    public Boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
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
                    .lastName(form.getLastName())
                    .firstName(form.getFirstName())
                    .sector(form.getSector())
                    .company(form.getCompany())
                    .password(passwordEncoder.encode(form.getPassword()))
                    .email(form.getEmail())
                    .enabled(true)
                    .build());
    }
}
