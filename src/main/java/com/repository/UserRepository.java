package com.repository;

import com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByName(String name);

    Optional<User> findById(Long id);

    void deleteById(Long id);


    @Override
    List<User> findAll();
}
