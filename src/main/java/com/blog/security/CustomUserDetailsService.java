package com.blog.security;

import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws  UsernameNotFoundException {
        //class will take user name or email id +password/sign in two ways.
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail) //email or name will be
                // passed as argument in method, it will check search in database. if record is found, keep that record
                // in user object.
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email:" + usernameOrEmail)); // in built exception - if user or id(record) not found
        return new org.springframework.security.core.userdetails.User(user.getEmail(), //
                // if user is found, it will return UserDetails object that has email,password and role.
                user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    //help to convert role into SimpleGrantedAuthority object.
    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}

