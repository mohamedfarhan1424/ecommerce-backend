package com.ecommerce.app.service;

import com.ecommerce.app.exception.RESTException;
import com.ecommerce.app.model.user.CreateUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    ResponseEntity<?> phoneLogin(String phoneNumber, String countryCode) throws RESTException;

    ResponseEntity<?> phoneVerify(String phoneNumber, Integer otp) throws RESTException;

    ResponseEntity<?> createUser(CreateUserRequest createUserRequest) throws RESTException;
}
