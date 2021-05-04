package com.rest;

import com.model.Role;
import com.model.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping("api/user")
public class AdminController {
    static Logger LOGGER;

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
        userService.editUser(user);
        return new ResponseEntity<User>(HttpStatus.OK);
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

    @RequestMapping("/index")//здесь ловим юзера с гугл и суём в бд
    public void getPrincipal(Principal principal){
        //пароль можно сгенерировать случайным образом, но затем вывести в логи. Случайное целое двузначное число, затем превратим его в строку
        Integer random_number = 10 + (int) (Math.random() * 99);
        String password = random_number.toString();
             User user = new User(principal.getName(), password ,new Role("ROLE_USER"));//соберём нового юзера. Кроме имени, нужен ещё пароль и права доступа
             userService.saveUser(user);
             LOGGER.log(Level.INFO,"Пароль нового пользователя:" + password);
    }
//BeanDefinitionStoreException - похоже на ошибку при чтении файла свойств.
}