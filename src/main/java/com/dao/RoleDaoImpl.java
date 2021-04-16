package com.dao;

import com.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RoleDaoImpl implements RoleDao{
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public Role getRoleByName(String role_name) {
        return (Role) entityManager.createQuery("SELECT r FROM Role r WHERE r.name LIKE :role_name")// поправить этот запрос
                .setParameter("role_name", role_name)
                .getSingleResult();
    }
}
