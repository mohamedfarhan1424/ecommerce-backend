package com.ecommerce.app.constant;

public class Endpoint {

    private Endpoint() {
        super();
    }

    public static final String API = "/api";

    public static final String USER_API = API + "/user";

    public static final String PHONE_LOGIN_API = USER_API + "/phone-login";

    public static final String PHONE_VERIFY_API = USER_API + "/phone-verify";

    public static final String CREATE_USER_API = USER_API + "/create-user";


}
