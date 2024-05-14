package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        User admin = new User(
                "admin",
                "admin",
                28,
                "admin",
                "admin",
                "admin@mail.com",
                Set.of(new Role(1L, "ROLE_ADMIN"))
        );

        User user = new User(
                "user",
                "user",
                28,
                "user",
                "user",
                "user@mail.com",
                Set.of(new Role(2L, "ROLE_USER"))
        );

        userService.deleteAllUsers();

        userService.saveUser(admin);
        userService.saveUser(user);
    }
}