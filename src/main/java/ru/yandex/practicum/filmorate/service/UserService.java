package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;


@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;
    private final Map<Integer, User> users = new HashMap<>();

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User create(User user) {
        validateUser(user);
        log.info("Создание нового пользователя: {}", user);
        if (users.containsKey(user.getId())) {
            log.warn("Пользователь с таким ID {} уже существует", user.getId());
            throw new NotFoundException("Пользователь с таким ID уже есть");
        }
        if (users.values().stream().anyMatch(u -> u.getLogin().equals(user.getLogin()))) {  // проверка есть ли такой логин
            log.warn("Такой пользователь {} уже существует", user.getLogin());
            throw new NotFoundException("Такой пользователь уже есть");
        }
        if (users.values().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) { // проверка есть ли такой Email
            log.warn("Такой email {} уже существует", user.getEmail());
            throw new NotFoundException("Такой email уже есть");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }


        return userStorage.create(user);
    }

    public User update(User user) {
        validateUser(user);
        return userStorage.update(user);
    }

    public User get(int id) {
        return userStorage.get(id);
    }

    public Set<User> getAll() {
        return userStorage.getAll();
    }

    public void addFriend(int userId, int friendId) {
        userStorage.addFriend(userId, friendId);
    }

    public void removeFriend(int userId, int friendId) {
        userStorage.removeFriend(userId, friendId);
    }

    public Set<User> getFriends(int userId) {
        return userStorage.getFriends(userId);
    }

    public Set<User> getCommonFriends(int userId, int otherId) {
        return userStorage.getCommonFriends(userId, otherId);
    }



    private void validateUser(User user) {
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
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
