# Phone Login API

## Overview
This API endpoint initiates the phone login process by sending an OTP (One-Time Password) to the provided phone number.

### Endpoint
`POST /api/user/phone-login`

## Usage
### Request Format
- **URL:** `http://localhost:8080/api/user/phone-login`
- **Method:** `POST`
- **Headers:**
    - `Content-Type: application/json`
- **Request Body:**
  ```json
  {
      "countryCode": "+91",
      "phoneNumber": "1234567890"
  }

### Request Format
| Field         | Type     | Description              |
|---------------|----------|--------------------------|
| countryCode   | String   | Country code             |
| phoneNumber   | String   | User's phone number      |


### Success Response
| Field       | Type     | Description                       |
|-------------|----------|-----------------------------------|
| message     | String   | Success message indicating OTP sent successfully |
| status      | Integer  | HTTP status code (200 for success)|

- **Status:** 200 OK
- **Body (JSON):**
  ```json
  {
    "message": "OTP sent successfully",
    "status": 200
  }
  ```


# User Phone Verification API

## Overview
This API endpoint verifies a user's phone number using an OTP (One-Time Password). It allows users to confirm their phone numbers for authentication and access purposes. For now use `1234`

### Endpoint
`POST /api/user/phone-verify`

## Usage
### Request Format
- **URL:** `http://localhost:8080/api/user/phone-verify`
- **Method:** `POST`
- **Headers:**
    - `Content-Type: application/json`
- **Request Body:**
  ```json
  {
      "phoneNumber": "1234567890",
      "otp": 1234
  }

### Request Format
| Field         | Type     | Description              |
|---------------|----------|--------------------------|
| phoneNumber   | String   | User's phone number       |
| otp           | Integer  | One-Time Password (OTP)  |


### Success Response
| Field       | Type     | Description                                            |
|-------------|----------|--------------------------------------------------------|
| message     | String   | Verification success message                           |
| status      | Integer  | HTTP status code (200 for success)                     |
| token       | String   | Authorization token for the user for further API calls |
| newUser     | Boolean  | Indicates if the user is new or not                    |

- **Status:** 200 OK
- **Body (JSON):**
  ```json
  {
    "message": "OTP verified successfully",
    "status": 200,
    "token": "eyJhbGciOiJIUzI1NiJ9....",
    "newUser": false
  }
  ```

### Error Responses
1. OTP not sent
    ```json
    {
        "type": "about:blank",
        "title": "Bad Request",
        "status": 400,
        "detail": "OTP not sent",
        "instance": "/api/user/phone-verify"
    }
    ```

2. OTP expired
    ```json
    {
        "type": "about:blank",
        "title": "Bad Request",
        "status": 400,
        "detail": "OTP expired",
        "instance": "/api/user/phone-verify"
    }
    ```

3. OTP not matched
    ```json
    {
        "type": "about:blank",
        "title": "Bad Request",
        "status": 400,
        "detail": "OTP not matched",
        "instance": "/api/user/phone-verify"
    }
    ```

# Create User API

## Overview
This API endpoint creates a new user with provided details.

### Endpoint
`POST /api/user/create-user`

## Usage
### Request Format
- **URL:** `http://localhost:8080/api/user/create-user`
- **Method:** `POST`
- **Headers:**
    - `Content-Type: application/json`
    - `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9....` (Authorization token)
- **Request Body:**
  ```json
  {
      "phoneNumber": "1234567890",
      "username": "Mohamed Farhan S",
      "email": "mohamedfarhan1424@gmail.com"
  }


### Request Format
| Field         | Type     | Description                  |
|---------------|----------|------------------------------|
| phoneNumber   | String   | User's phone number          |
| username      | String   | User's username              |
| email         | String   | User's email address         |

### Success Response
| Field       | Type     | Description                   |
|-------------|----------|-------------------------------|
| message     | String   | Success message               |
| status      | Integer  | HTTP status code (200 for success)|

- **Status:** 200 OK
- **Body (JSON):**
  ```json
  {
      "message": "User created successfully",
      "status": 200
  }

### Error Responses
1. Email is already registered
    ```json
    {
        "type": "about:blank",
        "title": "Bad Request",
        "status": 400,
        "detail": "Email is already registered",
        "instance": "/api/user/create-user"
    }
    ```

2. Phone number is not registered
    ```json
    {
        "type": "about:blank",
        "title": "Bad Request",
        "status": 400,
        "detail": "Phone number is not registered",
        "instance": "/api/user/create-user"
    }
    ```

3. Email is not valid
    ```json
    {
        "type": "about:blank",
        "title": "Bad Request",
        "status": 400,
        "detail": "Email is not valid",
        "instance": "/api/user/create-user"
    }
    ```

4. Phone number is not valid
    ```json
    {
        "type": "about:blank",
        "title": "Bad Request",
        "status": 400,
        "detail": "Phone number is not valid",
        "instance": "/api/user/create-user"
    }
    ```

## Notes
- The bearer authorization token is generated during phone verification.
- The bearer authorization token is valid for 30 minutes.
- The bearer authorization token is required for all API calls except phone login and phone verification.
- If no bearer authorization token is provided, the API will return a `403 Forbidden` error.
- Ensure the provided email and phone number formats are valid.
- Proper error handling should be implemented based on the specific error encountered during user creation.
