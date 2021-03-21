package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dao.UserDao;
import com.model.Role;
import com.model.User;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User saveUser(User user) {
        setUserRoles(user);

        String password = user.getPassword();

        String encodedPassword = passwordEncoder.encode(password);

        user.setPassword(encodedPassword);
        userDao.saveUser(user);
        return user;
    }

    @Override
    public User getUserById(long id) {

        return userDao.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void editUser(User user) {
        setUserRoles(user);
        String password = user.getPassword();
        User userFromDB = userDao.getUserById(user.getId());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!BCRYPT_PATTERN.matcher(password).matches()) {
            password = passwordEncoder.encode(password);
        }

        if(!password.equals(userFromDB.getPassword())){

            user.setPassword(password);
        }
        userDao.editUser(user);
    }

    @Transactional
    public void setUserRoles(User user) {
        user.setRoles(user
                .getRoles()
                .stream()
                .map(role -> userDao.getRoleByName(role.getName()))
                .collect(Collectors.toSet()));
    }


    @Override
    public void deleteUser(long id) {
        userDao.deleteUser(userDao.getUserById(id).getId());
    }

    @Override
    public User findUserByName(String name) {
        return userDao.findUserByUsername(name);

    }

    @Override
    public Role getRoleByName(String name) {
        return userDao.getRoleByName(name);
    }


}
