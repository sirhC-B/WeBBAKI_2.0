package de.thb.webbaki.service;

import de.thb.webbaki.entity.Privilege;
import de.thb.webbaki.entity.Role;
import de.thb.webbaki.entity.User;
import de.thb.webbaki.enums.RoleType;
import de.thb.webbaki.repository.PrivilegeRepository;
import de.thb.webbaki.repository.RoleRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class RoleService {
    private RoleRepository roleRepository;
    private PrivilegeRepository privilegeRepository;
    private User user;
    private Role role;

    public Optional<Role> getRole(long id){
        return roleRepository.findById(id);
    }

    public List<Role> getAllRoles(){
        return (List<Role>) roleRepository.findAll();
    }

    /*
    TODO finish adding Role to user if user doesnt already has a role.
     */
    public void addRole(String name, Collection<Privilege> privilege){
        if (user.getRoles().isEmpty()){
            user.setRoles();
        }
    }
}
