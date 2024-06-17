package ru.yandex.practicum.filmorate.controller;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.service.UserService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        log.info("Получен запрос на создание пользователя: {}", user);
        try {
            User createdUser = userService.create(user);
            log.info("Пользователь успешно создан: {}", createdUser);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (ValidationException e) {
            log.warn("Ошибка валидации при создании пользователя: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            log.error("Ошибка при создании пользователя: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        log.info("Получен запрос на получение пользователя по ID: {}", id);
        try {
            User user = userService.get(id);
            log.info("Пользователь успешно получен: {}", user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Ошибка при получении пользователя: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Void> addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен запрос на добавление друга: userId={}, friendId={}", id, friendId);
        try {
            userService.addFriend(id, friendId);
            log.info("Друг успешно добавлен: userId={}, friendId={}", id, friendId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Ошибка при добавлении друга: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Void> deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен запрос на удаление друга: userId={}, friendId={}", id, friendId);
        try {
            userService.removeFriend(id, friendId);
            log.info("Друг успешно удален: userId={}, friendId={}", id, friendId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Ошибка при удалении друга: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<User>> getFriends(@PathVariable int id) {
        log.info("Получен запрос на получение списка друзей: userId={}", id);
        try {
            List<User> friends = userService.getFriends(id);
            log.info("Список друзей успешно получен: userId={}", id);
            return new ResponseEntity<>(friends, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Ошибка при получении списка друзей: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<List<User>> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Получен запрос на получение списка общих друзей: userId={}, otherId={}", id, otherId);
        try {
            List<User> commonFriends = userService.getCommonFriends(id, otherId);
            log.info("Список общих друзей успешно получен: userId={}, otherId={}", id, otherId);
            return new ResponseEntity<>(commonFriends, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Ошибка при получении списка общих друзей: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}