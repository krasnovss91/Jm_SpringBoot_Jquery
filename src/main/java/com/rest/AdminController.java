package com.rest;

import com.model.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return new ResponseEntity<User>(this.userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/findAllUsers")
    public ResponseEntity<List<User>> findAllUsers() {
        return new ResponseEntity<List<User>>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> newUser(@RequestBody User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUsers(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/findUser")
    public ResponseEntity<User> userData(Principal principal) {
        return new ResponseEntity<User>(userService.findUserByName(principal.getName()), HttpStatus.OK);
    }

}