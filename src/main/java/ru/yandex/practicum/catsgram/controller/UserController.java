package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> findAll() {
        logger.info("Request received: GET /users");
        // Логирование количества пользователей
        logger.info("Current number of users: {}", userService.findAll().size());
        return userService.findAll();
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        logger.info("Request received: POST /users");
        userService.addUser(user);
        logger.info("New user: {}", user);
        return user;
    }

    @PutMapping
    public User put(@RequestBody User user) {
        userService.put(user);
        return user;
    }

    @GetMapping("/users/{email}")
    public User getByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }
}
