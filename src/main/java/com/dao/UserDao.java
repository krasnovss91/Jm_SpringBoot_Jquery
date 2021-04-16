package com.dao;

import com.model.Role;
import com.model.User;

import java.util.List;

public interface UserDao {
    User findUserByUsername(String username);

    void saveUser(User user);

    User getUserById(long id);

    List<User> getAllUsers();

    void editUser(User user);

    void deleteUser(long id);

    //Role getRoleByName(String name);

}