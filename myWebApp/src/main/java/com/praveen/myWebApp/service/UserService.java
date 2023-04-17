package com.praveen.myWebApp.service;



import com.praveen.myWebApp.entity.User;

import java.util.Optional;

public interface UserService {

    public Optional<User> findByUserName(String userName);

    public void save(User user);
}
