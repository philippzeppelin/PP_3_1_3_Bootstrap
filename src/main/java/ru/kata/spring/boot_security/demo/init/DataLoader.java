package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public DataLoader(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");

        User admin = new User(
                "admin",
                "admin",
                28,
                "admin",
                "admin",
                "admin@mail.com",
                Set.of(adminRole, userRole)
        );

        User user = new User(
                "user",
                "user",
                28,
                "user",
                "user",
                "user@mail.com",
                Set.of(userRole)
        );

        userService.deleteAllUsers();

        roleService.saveRole(adminRole);
        roleService.saveRole(userRole);

        userService.saveUser(admin);
        userService.saveUser(user);
    }
}