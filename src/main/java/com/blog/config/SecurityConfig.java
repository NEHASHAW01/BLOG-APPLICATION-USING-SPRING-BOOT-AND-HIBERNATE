package com.blog.config;

import com.blog.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/*config file - using confi class we can configure security concept.
we don't want any default configuration to be taken by spring security.
we want to configure url of project in such that we can control which url can be access by which user.
i.e. which urls can be accessed by admin, user. in order to do such configuration we go for config files. */

@Configuration // @Configuration annotation is used on a class to define configuration.

//there is a method (configure) present in  WebSecurityConfigurerAdapter . we override it and do the configuration - we change
// form based authentication to httpBasic (url) based authentication

@EnableWebSecurity // to enable custom security configuration. don't use default configuration.

@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
        // any config file can have Bean annotation
    PasswordEncoder passwordEncoder() { // when we call this method(from below), it will return bean.
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean // spring security is differnt library, for which spring IOC will not automatically create bean. we have to create bean manually.
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http                                      // serialization of steps - below are spring security filter chain
                .csrf().disable()         // csrf  - cross site request forgery - a security vulnerability that affect web application. in live environment make it enable.
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll() // url api/....(get mapping) can be accessed by all. without login all can be accessed.
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws //AuthenticationManagerBuilder - method. it give auth object. //this method will build the authentication or failed it
            Exception {
        auth.userDetailsService(userDetailsService) // take auth object address and call userDetailsService method(built-in method).
                // in this method we are supplying object of CustomUserDetailsService class that return UserDetails object that has email,password and role
                .passwordEncoder(passwordEncoder()); // UserDetails object is passed to here due to dependency injection.
    }
}

/* this is hard coded code - in-memory authentication.
    @Override
    @Bean
    protected UserDetailsService userDetailsService() { // we create two object not with new keyword but using builder method.
        UserDetails ramesh =           // creating user
                User.builder().username("pankaj").password(passwordEncoder() // we call passwordEncoder method. object addressed is returned.
                                                    // using address we are calling encode method (util package - main class). the method will encode the password.
                        .encode("password")).roles("USER").build(); // you cannot store password as it is
        // in database. when anyone get access of database then all passwords will be exposed. need to encode the password.
        UserDetails admin =
                User.builder().username("admin").password(passwordEncoder()
                        .encode("admin")).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(ramesh, admin);
    }
}*/

   /* in memmory Authentication means we are not using any database.
        all user name and password are stored in object(not in database) and we are testing security code
        it is beneficial when we want to built some part of code and test it. again built some part of code and test it.



/*
Authentication - if you enter userid and password, wrong one will give error.

authorization - security role
differnt user has different level of accesss

both are achieve by using spring security framework.

how do you secure your apis/endpoints - providing Authentication and authorization to api so that people can't access it directly.
none of url will work. it will work only after login.
 */
