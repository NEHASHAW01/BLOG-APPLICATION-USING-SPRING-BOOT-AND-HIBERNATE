package com.blog.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                authException.getMessage());
    }
}
// verify the incoming http request url is authenticated or not and it will throw exception.
/*this java class is the implementation of AuthenticationEntryPoint interface.
it act as entry point for handling authentication failure when accessing secured resources.-
whenever we are accessing something, unable to access  or failure happens, this class takes care of that. - throw  AuthenticationException when resources are accessed in unauthorised manner or with in valid credientials.
in spring security application, when a user tries to access a protected resourse without proper authentication, an "authenticationException" is thrown.
this exception is caught by the spring security filter chain.
 */

//if authenication is unsuccessful, ithis class will throw exception nd programm will stop.
// if authenication is successful, controll be be given to JwtAuthenticationFilter class. JwtTokenProvider class will generate token
// and send token to client, second time when client send request along with token. this token is get validated/verified by JwtTokenProvider class.
// if token is valid then server will send response back to client.


/*
spring security filter chain - it is responsible for proccessing and enforcing security rules on incoming http request
in web application. it will filter incoming http request based on rules
it helps us to apply certains rule on incoming https request like authentication, authorization, session management, csrf protection. on url.
for which we apply spring security filter chain in config file, i.e. configure which urls are accessed by whom
 why it is called filter chains - because commands are given as chains.
 */


//for the first time you are login or signup, we send login id and password to server, server verify that and send response back to postman.
//after sending request's response, server forget everything(client) // we have to login again each time we are reaching out to the server.
//jwt token works with spring security. - along with the request's response, server will send a jwt token also.
//now client has this token.  client made a request to server, it need to send the token along with the request.
//server will verify that token each time client sends a request and conclude that client was earlier authenticated. it will simply send the response back.

// what is jwt token?
//JWT token with spring security when applied what happens once we login the server sends back the jwt token so that using
//that token further request can be processed without authentication, because now authentication can be done by just a token id
//rather than giving login details every time.

//this is used in intregration with spring security. once we login, jwt token is generated from server side, so that
// subsequent request can be made just with token and subsequent login is not required.


// without spring security, JWT token will not work.

