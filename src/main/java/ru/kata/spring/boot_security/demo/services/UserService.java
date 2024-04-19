package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

@Service
public interface UserService {
    List<User> findAll();

    User findUserById(long id);

    void saveUser(User user);

    void deleteUser(long id);

    User findUserByUsername(String username);
}