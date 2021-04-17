package com.service;

import com.dao.RoleDao;
import com.model.Role;
import com.model.User;
import com.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
   // private RoleDao roleDao;
    RoleRepository roleRepository;

    @Override
    public void setUserRoles(User user) {
        user.setRoles(user
                .getRoles()
                .stream()
                .map(role -> roleDao.getRoleByName(role.getName()))
                .collect(Collectors.toSet()));
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByRole(name);
    }

}
