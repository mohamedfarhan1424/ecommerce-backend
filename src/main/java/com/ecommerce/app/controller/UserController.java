package com.ecommerce.app.controller;

import com.ecommerce.app.constant.Endpoint;
import com.ecommerce.app.exception.RESTException;
import com.ecommerce.app.model.user.CreateUserRequest;
import com.ecommerce.app.model.user.PhoneLoginRequest;
import com.ecommerce.app.model.user.PhoneVerifyRequest;
import com.ecommerce.app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(Endpoint.PHONE_LOGIN_API)
    public ResponseEntity<?> phoneLogin(
            @RequestBody PhoneLoginRequest phoneLoginRequest
    ) throws RESTException {
        return userService.phoneLogin(phoneLoginRequest.getPhoneNumber(),phoneLoginRequest.getCountryCode());
    }

    @PostMapping(Endpoint.PHONE_VERIFY_API)
    public ResponseEntity<?> phoneVerify(
            @RequestBody PhoneVerifyRequest phoneVerifyRequest
    ) throws RESTException {
        return userService.phoneVerify(phoneVerifyRequest.getPhoneNumber(), phoneVerifyRequest.getOtp());
    }

    @PostMapping(Endpoint.CREATE_USER_API)
    public ResponseEntity<?> createUser(
            @RequestBody CreateUserRequest createUserRequest
    ) throws RESTException {
        return userService.createUser(createUserRequest);
    }
}
