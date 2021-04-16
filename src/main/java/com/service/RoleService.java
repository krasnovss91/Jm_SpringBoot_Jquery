package com.service;

import com.model.Role;
import com.model.User;

public interface RoleService {
    void setUserRoles(User user);

    Role getRoleByName(String name);
}
