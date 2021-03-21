package com.rest;

import com.dto.exceptions.UserNotFoundByIdException;
import com.model.User;


import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;

import java.util.List;


@RestController
@RequestMapping("api/")
public class AdminController {

    @Autowired
    private UserService userService;


    @GetMapping("{id}")
    public User getById(@PathVariable Long id) throws UserNotFoundByIdException {
        return this.userService.getUserById(id);
    }

    @GetMapping("/users")
    public List<User> findAllUsers() {
        List<User> list = userService.getAllUsers();
        return list;
    }

    @PostMapping("/add")
    public User newUser(@RequestBody User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        User user_1 =userService.saveUser(user);
        return user_1;
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userService.saveUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUsers(@PathVariable Long id) throws UserNotFoundByIdException{
        userService.deleteUser(id);
    }

    @GetMapping("/user")
    public User userData(Principal principal) {
        return userService.findUserByName(principal.getName());
    }

}