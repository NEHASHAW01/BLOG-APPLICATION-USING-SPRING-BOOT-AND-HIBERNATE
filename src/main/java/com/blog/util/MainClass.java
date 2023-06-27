package com.blog.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.awt.image.BandCombineOp;

public class MainClass {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //to encode a password there is a builtin object BCryptPasswordEncoder. this object is present in spring security framework.
        // object address is store in PasswordEncoder encode. it has a method in it - encode, which help in encoding the password.
        System.out.println(passwordEncoder.encode("testing")); // this encoded password will go into datbase.
        // whatever we sign-up , password first get encoded then it got saved in database.

    }
}
