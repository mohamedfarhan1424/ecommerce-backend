package com.ecommerce.app.service.impl;

import com.ecommerce.app.constant.Roles;
import com.ecommerce.app.entity.Role;
import com.ecommerce.app.repository.RoleRepository;
import com.ecommerce.app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public void saveRoles() {
        List<String> roles = List.of(Roles.ADMIN, Roles.USER);

        roles.forEach(role -> {
            if(roleRepository.findRoleByRoleName(role) == null) {

                Role newRole = new Role();
                newRole.setRoleName(role);

                roleRepository.save(newRole);
            }
        });

    }
}
