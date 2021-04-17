package com.repository;


import com.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    @Override
    List<Role> findAll();

    Role findByRole(String role);

    @Override
    Role getOne(Integer integer);
}
