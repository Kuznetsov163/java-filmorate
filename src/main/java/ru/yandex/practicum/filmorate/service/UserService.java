package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;


@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(User user) {
        validateUser(user);
        log.info("Получен запрос на создание пользователя: {}", user);
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        user = userStorage.createUser(user);
        log.info("Пользователь '{}' успешно создан.", user);

        return user;
    }

    public User updateUser(User user) {

        if (user.getId() == 0) {
            throw new ValidationException("Id фильма должен быть указан");
        }
        validateUser(user);
        get(user.getId());
        log.info("Получен запрос на обновление пользователя: {}", user);
        User usUp = userStorage.getUserId(user.getId()).get();
        usUp = usUp.toBuilder()
                .login(user.getLogin())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .name(user.getName())
                .build();
        userStorage.updateUser(usUp);
        log.info("Пользователь '{}' успешно обновлен", user);

        return user;
    }


    public Collection<User> getAllUser() {
        log.info("Получен запрос на получение всех пользователей");

        return userStorage.getAllUser();
    }

    public void get(long id) {
        if (userStorage.getUserId(id).isEmpty()) {
            log.warn("Пользователь с ID {} не найден", id);
            throw new NotFoundException("Пользователь с id = " + id + " не найден");
        }
    }



    public void addFriend(long userId, long friendId) {
        log.info("Получен запрос на добавление друга: userId={}, friendId={}", userId, friendId);
        get(userId);
        get(friendId);
        userStorage.addFriend(userId, friendId);
        userStorage.addFriend(friendId, userId);
        log.info("Друг успешно добавлен: userId={}, friendId={}", userId, friendId);
    }

    public void removeFriend(long userId, long friendId) {
        log.info("Получен запрос на удаление друга: userId={}, friendId={}", userId, friendId);
        get(userId);
        get(friendId);
        userStorage.removeFriend(userId, friendId);
        userStorage.removeFriend(friendId, userId);
        log.info("Друг успешно удален: userId={}, friendId={}", userId, friendId);

    }

    public Set<User> getFriends(long id) {
        log.info("Получен запрос на получение друзей пользователя с id {}", id);
        get(id);
        return userStorage.getFriends(id);
    }

    public Set<User> getCommonFriends(long id, long otherId) {
        log.info("Получен запрос на получение общих друзей пользователей {} и {}", id, otherId);
        get(id);
        get(otherId);
        return userStorage.getCommonFriends(id, otherId);
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
