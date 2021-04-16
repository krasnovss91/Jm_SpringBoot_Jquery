package com.rest;

import com.model.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("api/user")
public class AdminController {

    @Autowired
    private UserService userService;


    @GetMapping("{id}")
    public User getById(@PathVariable Long id) {
        return this.userService.getUserById(id);
    }

    @GetMapping("/findAllUsers")
    public List<User> findAllUsers() {
        List<User> list = userService.getAllUsers();
        return list;
    }

    @PostMapping("/add")
    public User newUser(@RequestBody User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userService.saveUser(user);
    }

    @PutMapping("/edit")
    public User updateUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUsers(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/findUser")
    public User userData(Principal principal) {
        return userService.findUserByName(principal.getName());
    }

}