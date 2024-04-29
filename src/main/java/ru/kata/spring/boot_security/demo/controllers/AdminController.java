package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.nio.file.AccessDeniedException;

@Controller
@RequestMapping("/admin")
public class AdminController { // TODO Админский контроллер
    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/login")
//    public String showLogin() {
//        return "auth/login";
//    }

    @GetMapping
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());

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
    public String createUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/new";
        }

        userService.saveUser(user);

        return "redirect:/admin";
    }

    @PatchMapping("/{id}") // TODO исправить баг с редактированием пользователя
    // There was an unexpected error (type=Method Not Allowed, status=405).
    public String updateUser(@ModelAttribute("user") User updatedUser,
                             BindingResult bindingResult,
                             @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }

        User existingUser = userService.findUserById(id);

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());

        userService.saveUser(existingUser);

//        userService.updateUser(id, user);

        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String showEditUserForm(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.findUserById(id));

        return "admin/edit";
    }

    @DeleteMapping("/{id}") // TODO Доделать
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);

        return "redirect:/admin";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException() {
        return "redirect:/access_denied";
    }
}