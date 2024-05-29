package ru.yandex.practicum.filmorate.controller;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int nextId = 1;

    @PostMapping
    public User createUser(@RequestBody User user) {
        log.info("Получен запрос на создание пользователя: {}", user);
        validateUser(user);

        if (users.values().stream().anyMatch(u -> u.getLogin().equals(user.getLogin()))) {  // проверка есть ли такой логин
            log.warn("Такой пользователь {} уже существует", user.getLogin());
            throw new RuntimeException("Такой пользователь уже есть");
        }
        if (users.values().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) { // проверка есть ли такой Email
            log.warn("Такой email {} уже существует", user.getEmail());
            throw new RuntimeException("Такой email уже есть");
        }

        user.setId(nextId++);
        users.put(user.getId(), user);
        log.info("Пользователь успешно создан: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        log.info("Получен запрос на обновление пользователя: {}", user);
        validateUser(user);
        users.put(user.getId(), user);
        log.info("Пользователь успешно обновлен: {}", user);
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("Получен запрос на получение всех пользователей");
        return new ArrayList<>(users.values());
    }

    private void validateUser(User user) {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.warn("Электронная почта должна быть не пустой и содержать символ '@'");
            throw new ValidationException("Электронная почта должна быть не пустой и содержать символ '@'");
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.warn("Логин не может быть пустым и содержать пробелы");
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getBirthday().isAfter(LocalDate.now()) || user.getBirthday().isEqual(LocalDate.now())) {
            log.warn("Дата рождения не может быть в настоящем или будущем");
            throw new ValidationException("Дата рождения не может быть в настоящем или будущем");
        }

    }
}
