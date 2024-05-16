package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showAllUsers(Principal principal, Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", userService.findUserByUsername(principal.getName()));
        model.addAttribute("roles", roleService.getRoleSet());

        return "admin/index";
    }

    @PostMapping()
    public String addNewUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);

        return "redirect:/admin";
    }


    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User updatedUser,
                             @RequestParam("id") long id) {
        userService.update(id, updatedUser);

        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);

        return "redirect:/admin";
    }
}