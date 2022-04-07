package de.thb.webbaki.service;

import de.thb.webbaki.entity.Privilege;
import de.thb.webbaki.entity.Role;
import de.thb.webbaki.entity.User;
import de.thb.webbaki.repository.PrivilegeRepository;
import de.thb.webbaki.repository.RoleRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class RoleService {
    private RoleRepository roleRepository;
    private PrivilegeRepository privilegeRepository;

    public Optional<Role> getRole(long id){
        return roleRepository.findById(id);
    }

    public List<Role> getAllRoles(){
        return (List<Role>) roleRepository.findAll();
    }

    public void addRole(String name, Collection<User> user, Collection<Privilege> privilege){
        if ()
    }
}
