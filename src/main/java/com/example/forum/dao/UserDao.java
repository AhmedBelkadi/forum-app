package com.example.forum.dao;

import com.example.forum.models.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> getUserByEmail(String email);

    boolean checkPassword(User user, String password);

    void saveUser(User user);
}

