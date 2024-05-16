package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

@Service
public interface UserService {
    List<User> findAll();

    void saveUser(User user);

    void update(long id, User updatedUser);

    void deleteUser(long id);

    void deleteAllUsers();

    User findUserByUsername(String username);
}