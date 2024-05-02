package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

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

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));

        return "admin/show";
    }

    @GetMapping("/new")
    public String showNewUserForm(@ModelAttribute("user") User user) {
        return "admin/new";
    }

    @PostMapping()
    public String addNewUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);

        return "redirect:/admin";
    }


    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User updatedUser,
                             @PathVariable("id") long id) {
        Set<Role> roles = roleService.getRoleSet();
        updatedUser.setRoles(roles);
        userService.update(id, updatedUser);

        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String showEditUserForm(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.findUserById(id));

        return "admin/edit";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);

        return "redirect:/admin";
    }
}