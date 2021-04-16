package com.service;

import com.dao.RoleDao;
import com.model.Role;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleDao roleDao;
    @Override
    @Transactional
    public void setUserRoles(User user) {
        user.setRoles(user
                .getRoles()
                .stream()
                .map(role -> roleDao.getRoleByName(role.getName()))
                .collect(Collectors.toSet()));
    }
    @Override
    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }

}
