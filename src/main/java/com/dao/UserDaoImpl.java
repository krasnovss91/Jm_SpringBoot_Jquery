package com.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.model.Role;
import com.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Transactional
@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User findUserByUsername(String username) {

        Query query = entityManager.createQuery("SELECT e FROM User e join fetch e.roles where e.username =: username");

        query.setParameter("username", username);

        User result = null;
        try {
            result = (User) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    @Override
    public void saveUser(User user) {

        if (user.getUsername() != null) {
            entityManager.persist(user);
        }

    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {

        return entityManager.createQuery("SELECT e FROM User e", User.class).getResultList();

    }

    @Override
    public Role getRoleByName(String role_name) {
        return (Role) entityManager.createQuery("SELECT r FROM Role r WHERE r.name LIKE :role_name")
                .setParameter("role_name", role_name)
                .getSingleResult();
    }

    @Override
    public void editUser(User user) {

        if (user.getId() != 0) {
            entityManager.merge(user);
        }

    }

    @Override
    public void deleteUser(long id) {

        User userToBeDeleted = getUserById(id);

        if (userToBeDeleted != null) {
            entityManager.remove(userToBeDeleted);
        }

    }

}
