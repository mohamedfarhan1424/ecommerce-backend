package com.ecommerce.app.model.user;

import com.ecommerce.app.model.common.PlatformResponse;

public class PhoneVerifyResponse extends PlatformResponse {

    private String token;

    private boolean newUser;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }
}
