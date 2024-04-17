package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping
    public String showUserDetails() {
        return "user/userInfo";
    }
}

/**
 * Пользователь с ролью user должен иметь доступ только к своей домашней
 * странице /user, где выводятся его данные. Доступ к этой странице должен
 * быть только у пользователей с ролью user и admin. Не забывайте про несколько
 * ролей у пользователя!
 */