package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController { // TODO Админский контроллер
    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

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
    public String updateUser(@ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }

        userService.updateUser(id, user);

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
}

/**
 * Все CRUD-операции и страницы для них должны быть доступны только
 * пользователю с ролью admin по url: /admin/.
 */