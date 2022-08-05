package de.thb.webbaki.configuration;

import de.thb.webbaki.entity.Privilege;
import de.thb.webbaki.entity.Role;
import de.thb.webbaki.entity.User;
import de.thb.webbaki.repository.PrivilegeRepository;
import de.thb.webbaki.repository.RoleRepository;
import de.thb.webbaki.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        Privilege changePassword = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");
        Privilege confirmUserPrivilege = createPrivilegeIfNotFound("CONFIRM_USER_PRIVILEGE");


        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege, changePassword, confirmUserPrivilege);
        List<Privilege> userPrivileges = Arrays.asList(readPrivilege, changePassword);

        Role superAdmin = createRoleIfNotFound("SUPERADMIN", adminPrivileges);
        Role defaultUser = createRoleIfNotFound("DEFAULT_USER", userPrivileges);
        Role branchenAdmin = createRoleIfNotFound("BRANCHENADMIN", userPrivileges);
        Role sektorenAdmin = createRoleIfNotFound("SEKTORENADMIN", userPrivileges);
        Role bundesAdmin = createRoleIfNotFound("BUNDESADMIN", userPrivileges);
        Role geschäftsstelle = createRoleIfNotFound("GESCHÄFTSSTELLE", userPrivileges);
        Role kritisBetreiber = createRoleIfNotFound("KRITIS_BETREIBER", userPrivileges);

        createUserIfNotFound("Christian", "Schramm", "Passwort", "schrammbox@gmail.com",
                new ArrayList<>(Arrays.asList(superAdmin)), true);

        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    User createUserIfNotFound(String firstName, String lastName, String password, String email, Collection<Role> roles,
                              boolean enabled){
        User user = userRepository.findByEmail(email);
        if(user == null){
            user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setRoles(roles);
            user.setEnabled(enabled);

            userRepository.save(user);
        }
        return user;
    }
}