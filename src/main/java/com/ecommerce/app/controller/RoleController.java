package com.ecommerce.app.controller;


import com.ecommerce.app.service.RoleService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostConstruct
    public void saveRoles() {
        roleService.saveRoles();
    }

}
