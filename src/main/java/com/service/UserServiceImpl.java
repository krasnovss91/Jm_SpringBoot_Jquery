package com.service;

import com.dao.UserDao;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

@Transactional
@Service
public class UserServiceImpl implements UserService {

      private UserDao userDao;

    @Autowired
    private RoleService roleService;

    Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User saveUser(User user) {
        roleService.setUserRoles(user);
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
        roleService.setUserRoles(user);
        String password = user.getPassword();
        User userFromDB = userDao.getUserById(user.getId());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!BCRYPT_PATTERN.matcher(password).matches()) {
            password = passwordEncoder.encode(password);
        }

        if (!password.equals(userFromDB.getPassword())) {

            user.setPassword(password);
        }
        userDao.editUser(user);
    }

    @Override
    public void deleteUser(long id) {
        userDao.deleteUser(userDao.getUserById(id).getId());
    }

    @Override
    public User findUserByName(String name) {
        return userDao.findUserByUsername(name);

    }


}


