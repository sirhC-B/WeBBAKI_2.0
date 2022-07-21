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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

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
    public void onApplicationEvent(final ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }
        final Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        final Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        final Privilege passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");

        final List<Privilege> adminPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
        final List<Privilege> userPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, passwordPrivilege));

        final Role adminRole = createRoleIfNotFound("SUPERADMIN", adminPrivileges);
        final Role userRole = createRoleIfNotFound("KRITIS_BETREIBER", userPrivileges);

        createUserIfNotFound("Schramm", "Christian", "Telekommunikation", "Branche Telekom",
        "Meta", "Passwort", new ArrayList<>(Arrays.asList(adminRole)), "schrammbox@gmail.com",true);

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
    Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    User createUserIfNotFound(final String lastName, final String firstName, final String sector, final String branche,
                              final String company, final String password, final Collection<Role> roles, final String email,
                              final boolean enabled) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setLastName(lastName);
            user.setFirstName(firstName);
            user.setSector(sector);
            user.setBranche(branche);
            user.setCompany(company);
            user.setPassword(passwordEncoder.encode(password));
            user.setEnabled(enabled);
            user.setEmail(email);
        }
        user.setRoles(roles);
        user = userRepository.save(user);

        return user;
    }
}