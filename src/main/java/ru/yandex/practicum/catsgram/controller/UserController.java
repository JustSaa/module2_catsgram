package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.User;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final Map<String, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        logger.info("Request received: GET /users");
        // Логирование количества пользователей
        logger.info("Current number of users: {}", users.size());
        return users.values();
    }

    @PostMapping
    public User addUser(@RequestBody User user) throws UserAlreadyExistException, InvalidEmailException {
        logger.info("Request received: POST /users");
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidEmailException("Адрес электронной почты не может быть пустым.");
        }

        if (users.containsKey(user.getEmail())) {
            throw new UserAlreadyExistException("Пользователь с электронной почтой " +
                    user.getEmail() + " уже зарегистрирован.");
        }
        logger.info("New user: {}", user);
        users.put(user.getEmail(), user);
        return user;
    }

    @PutMapping
    public User put(@RequestBody User user) throws InvalidEmailException {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidEmailException("Адрес электронной почты не может быть пустым.");
        }
        users.put(user.getEmail(), user);
        return user;
    }

    class UserAlreadyExistException extends Exception {
        public UserAlreadyExistException(String message) {
            super(message);
        }
    }

    class InvalidEmailException extends Exception {
        public InvalidEmailException(String message) {
            super(message);
        }
    }
}
