package com.blog.payload;

import lombok.Data;

@Data
public class LoginDto { // verifying user email id and password.
    private String usernameOrEmail;
    private String password;
}

