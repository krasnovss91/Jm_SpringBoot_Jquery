package com.rest;

import com.model.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("api/user")
public class AdminController {

    @Autowired
    private UserService userService;

//вместо юзера возвращать запрос целиком
 //ResponceEntity.ok().head().body()
    @GetMapping("{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {

        //return this.userService.getUserById(id);
        return new ResponseEntity<User>(this.userService.getUserById(id),HttpStatus.OK);
    }

    @GetMapping("/findAllUsers")
    public List<User> findAllUsers() {
        List<User> list = userService.getAllUsers();
        return list;
    }

    @PostMapping("/add")
    public ResponseEntity<User> newUser(@RequestBody User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        //return userService.saveUser(user);
        return new ResponseEntity<User>(userService.saveUser(user),HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return new ResponseEntity<User>(userService.saveUser(user),HttpStatus.OK);
        //return userService.saveUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUsers(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/findUser")
    public ResponseEntity<User> userData(Principal principal) {
        //return userService.findUserByName(principal.getName());
        return new ResponseEntity<User>(userService.findUserByName(principal.getName()),HttpStatus.OK);
    }

}