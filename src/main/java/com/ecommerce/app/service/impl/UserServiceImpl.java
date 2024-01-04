package com.ecommerce.app.service.impl;

import com.ecommerce.app.constant.Roles;
import com.ecommerce.app.entity.User;
import com.ecommerce.app.exception.RESTException;
import com.ecommerce.app.model.common.PlatformResponse;
import com.ecommerce.app.model.user.CreateUserRequest;
import com.ecommerce.app.model.user.PhoneVerifyResponse;
import com.ecommerce.app.repository.RoleRepository;
import com.ecommerce.app.repository.UserRepository;
import com.ecommerce.app.service.UserService;
import com.ecommerce.app.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    public static final String PHONE_NUMBER_REGEX = "^[0-9]+$";

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._:$!%-]+@[a-zA-Z0-9.-]+.[a-zA-Z]$";
    private final UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findByPhoneNumber(username);

        if(userOptional.isPresent()) {
            return new UserDetailsService(userOptional.get());
        }

        throw new UsernameNotFoundException("User not found");

    }

    @Override
    public ResponseEntity<?> phoneLogin(String phoneNumber, String countryCode) throws RESTException {

        if(phoneNumber.length() != 10 | !phoneNumber.matches(PHONE_NUMBER_REGEX)) {
            throw new RESTException("Phone number is not valid");
        }


        Integer otp = (int) (Math.random() * 9000) + 1000;
        Date expiresIn = new Date(System.currentTimeMillis() + 60000); // TODO: For now OTP expires in 1 minute
        User user;
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        if(userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            user = new User();
            user.setPhoneNumber(phoneNumber);
            user.setCountryCode(countryCode);
        }
        user.setOtp(otp);
        user.setExpiresIn(expiresIn);
        userRepository.save(user);
        // TODO: Need to implement SMS service
        PlatformResponse platformResponse = new PlatformResponse();
        platformResponse.setMessage("OTP sent successfully");
        platformResponse.setStatus(200);
        return ResponseEntity.ok().body(platformResponse);
    }

    @Override
    public ResponseEntity<?> phoneVerify(String phoneNumber, Integer otp) throws RESTException {
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);

        if(userOptional.isPresent()) {

            User user = userOptional.get();

            if(user.getOtp() == null || user.getExpiresIn() == null) {
                throw new RESTException("OTP not sent");
            }



            if (otp.equals(1234)) {  // TODO: Change this after implementing SMS service

                if (user.getExpiresIn().after(new Date())) {
                    PhoneVerifyResponse phoneVerifyResponse = new PhoneVerifyResponse();

                    user.setOtp(null);
                    user.setExpiresIn(null);

                    if(user.getRoles().isEmpty()) {
                        user.getRoles().add(roleRepository.findRoleByRoleName(Roles.USER));
                        phoneVerifyResponse.setNewUser(true);
                    }else{
                        phoneVerifyResponse.setNewUser(false);
                    }

                    userRepository.save(user);

                    String token = jwtUtil.generateTokenFor30Minutes(user); // TODO: Check for token expiration

                    phoneVerifyResponse.setToken(token);
                    phoneVerifyResponse.setMessage("OTP verified successfully");
                    phoneVerifyResponse.setStatus(200);

                    return ResponseEntity.ok().body(phoneVerifyResponse);
                }

                throw new RESTException("OTP expired");
            }

            throw new RESTException("OTP not matched");
        }

        throw new RESTException("Phone number is wrong");
    }

    @Override
    public ResponseEntity<?> createUser(CreateUserRequest createUserRequest) throws RESTException {
        String email = createUserRequest.getEmail();
        String phoneNumber = createUserRequest.getPhoneNumber();

        if(phoneNumber.length() != 10 | !phoneNumber.matches(PHONE_NUMBER_REGEX)) {
            throw new RESTException("Phone number is not valid");
        }

        if(!email.matches(EMAIL_REGEX)) {
            throw new RESTException("Email is not valid");
        }

        Optional<User> userOptionalForEmail = userRepository.findByEmail(email);

        if(userOptionalForEmail.isPresent()){
            throw new RESTException("Email is already registered");
        }

        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);

        if(userOptional.isEmpty()){
            throw new RESTException("Phone number is not registered");
        }

        User user = userOptional.get();

        user.setEmail(email);
        user.setUsername(createUserRequest.getUsername());

        userRepository.save(user);

        PlatformResponse platformResponse = new PlatformResponse();
        platformResponse.setMessage("User created successfully");
        platformResponse.setStatus(200);

        return ResponseEntity.ok().body(platformResponse);

    }

}
