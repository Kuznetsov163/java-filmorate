package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
            throw new ValidationException("Электронная почта должна быть не пустой и содержать символ '@'");
        }
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if ((user.getName() == null) || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidation(final ValidationException e) {
        return Map.of("error", "Произошла ошибка валидации одного из параметров: " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(final NotFoundException e) {
        return Map.of("error", "Не найден переданный параметр.");
    }
}
