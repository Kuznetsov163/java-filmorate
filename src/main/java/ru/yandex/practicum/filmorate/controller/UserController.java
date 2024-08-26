package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import ru.yandex.practicum.filmorate.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import jakarta.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/users")
@Qualifier("UserDbStorage")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        return userService.addFriend(id,friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getUserFriends(@PathVariable Long id) {
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.getCommonFriends(id, otherId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.removeFriend(id, friendId);

    }

    @PostMapping
    public User createUser(@RequestBody @Valid User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы");
        } else if (user.getName() == null) {
            user.setName(user.getLogin());
        } else if (!user.getEmail().contains("@")) {
            throw new ValidationException("Email должен содержать @");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        userService.createUser(user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        return userService.updateUser(user);
    }


    @GetMapping
    public Collection<User> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public Optional<User> findOne(@PathVariable Long id) {
        return userService.findOne(id);
    }

}