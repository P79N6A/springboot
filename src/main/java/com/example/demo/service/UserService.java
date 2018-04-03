package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;

public interface UserService {
    boolean addUser(User user);

    List<User> findAll();

    User findById(String id);
    boolean delUser(String id);

}
