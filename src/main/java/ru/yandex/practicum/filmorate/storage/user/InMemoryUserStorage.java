package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import jakarta.validation.Valid;

@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private long id = 1;

    @PostMapping
    public User createUser(@RequestBody @Valid User user) {
        validateUser(user);
        user.setId(id++);
        users.put(user.getId(), user);
        log.info("Добавлен пользователь: " + user);
        return user;

    }

    @PutMapping
    public User updateUser(User user) {
        if (user.getId() == 0) {
            throw new NotFoundException("Id фильма должен быть указан");
        }
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы");
        } else if (user.getName() == null) {
            user.setName(user.getLogin());
        }  else if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Не найден изменяемый пользователь");
        }
        log.info("Изменён пользователь: " + user);
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping
    public Collection<User> getAllUser() {
        if (users.isEmpty()) {
            log.info("Список пользователей пуст");
            throw new NotFoundException("Список пользователей пуст");
        }
        return users.values();
    }

    @Override
    public Optional<User> getUserId(Long userId) {
        return Optional.ofNullable(users.get(userId));
    }



    @Override
    public void addFriend(Long oneUser, Long twoId) {
    }


    @Override
    public void removeFriend(Long userOneId, Long userTwoId) {
    }

    @Override
    public Collection<User> getCommonFriends(Long userOneId, Long userTwoId) {
        return null;
    }

    @Override
    public Collection<User> getFriends(Long id) {
        return null;
    }

    private void validateUser(User user) {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.warn("Электронная почта должна быть не пустой и содержать символ '@'");
            throw new ValidationException("Электронная почта должна быть не пустой и содержать символ '@'");
        }
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            log.warn("Логин не может быть пустым и содержать пробелы");
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if ((user.getName() == null) || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Дата рождения не может быть в будущем");
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
    }
}
