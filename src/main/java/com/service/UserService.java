package com.service;

import com.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    User getUserById(long id);

    List<User> getAllUsers();

    void editUser(User user);

    void deleteUser(long id);

    User findUserByName(String name);

}
