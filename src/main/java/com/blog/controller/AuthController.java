package com.blog.controller;

import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.payload.JWTAuthResponse;
import com.blog.payload.LoginDto;
import com.blog.payload.SignUpDto;
import com.blog.repository.RoleRepository;
import com.blog.repository.UserRepository;
import com.blog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin") // it takes content in LoginDto

    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){ // JWTAuthResponse this is response we send back from server. this is payload.
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));
    } // first this will be called then JwtAuthenticationEntryPoint class.


    /*
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto
                                                           loginDto){
        Authentication authentication = authenticationManager.authenticate( //AuthenticationManager is in-built
                new
                        UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), // take user name and email and password and supply to in built class - UsernamePasswordAuthenticationToken
                        loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication); //if correct authentication, then it will set authenticateion.
        return new ResponseEntity<>("User signed-in successfully!.",HttpStatus.OK);
    } */

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        // add check for username exists in a DB
        if(userRepository.existsByUsername(signUpDto.getUsername())){ // userRepository first check weather user exist or not in db.
            return new ResponseEntity<>("Username is already taken!", //if exist it will return true. and give this message
                    HttpStatus.BAD_REQUEST);
        }
        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){ // userRepository first check weather email exist or not in db.
            return new ResponseEntity<>("Email is already taken!",//if exist it will return true. and give this message
                    HttpStatus.BAD_REQUEST);
        }

        // if both above condition return false, that is userid and email do not exist. thn new user object will be created.
        // create user object
        User user = new User();
        user.setName(signUpDto.getName()); // user object will copy data from SignUpDto
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());

        user.setPassword(passwordEncoder.encode(signUpDto.getPassword())); // password encorder will encode the password and keep it here.

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));  //it will set the role. collection here convert Role object into set.
        // user.setRoles takes set value so we need to convert role object into  set.
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully",
                HttpStatus.OK);
    }
}
