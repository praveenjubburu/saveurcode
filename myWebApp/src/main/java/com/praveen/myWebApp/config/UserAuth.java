package com.praveen.myWebApp.config;


import com.praveen.myWebApp.entity.User;
import com.praveen.myWebApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserAuth implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user=userService.findByUserName(username);

        System.err.println("You are here");

        if(user.isEmpty()){
            throw new UsernameNotFoundException("User Name is Not found in the database");
        }

        return new UserInfo(user.get());
    }
}
