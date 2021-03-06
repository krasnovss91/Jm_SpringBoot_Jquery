package com.dao;

import com.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

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
