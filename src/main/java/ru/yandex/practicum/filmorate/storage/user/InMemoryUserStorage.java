package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.*;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    @Override
    public User create(User user) {
        user.setId(id++);
        users.put(user.getId(), user);
        log.info("Пользователь '{}' успешно создан", user.getLogin());
        return user;

    }

    @Override
    public User update(User user) {
        if (!users.containsKey(user.getId())) {
            log.warn("Пользователь с id {} не найден", user.getId());
            throw new NotFoundException("Пользователь с таким id не найден");
        }
        users.put(user.getId(), user);
        log.info("Пользователь '{}' успешно обновлен", user.getLogin());
        return user;
    }


    @Override
    public User get(int id) {
        if (!users.containsKey(id)) {
            log.warn("Пользователь с id {} не найден", id);
            throw new NotFoundException("Пользователь с таким id не найден");
        }

        return users.get(id);
    }


    @Override
    public Set<User> getAll() {
        log.info("Получение списка всех пользователей");
        return new HashSet<>(users.values());
    }


    @Override
    public void addFriend(int userId, int friendId) {
        User user = users.get(userId);
        User friend = users.get(friendId);
        if (user == null || friend == null) {
            log.warn("Пользователь с id {} или {} не найден", userId, friendId);
            throw new NotFoundException("Пользователь с таким id не найден");
        }
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
        log.info("Пользователь {} добавил в друзья пользователя {}", userId, friendId);
    }


    @Override
    public void removeFriend(int userId, int friendId) {
        User user = users.get(userId);
        User friend = users.get(friendId);
        if (user == null || friend == null) {
            log.warn("Пользователь с id {} или {} не найден", userId, friendId);
            throw new NotFoundException("Пользователь с таким id не найден");
        }
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
        log.info("Пользователь {} удалил из друзей пользователя {}", userId, friendId);
    }


    @Override
    public Set<User> getFriends(int userId) {
        User user = users.get(userId);
        if (user == null) {
            log.warn("Пользователь с id {} не найден", userId);
            throw new NotFoundException("Пользователь с таким id не найден");
        }
        log.info("Список друзей пользователя ");
        return user.getFriends().stream()
                .map(users::get)
                .collect(Collectors.toSet());
    }


    @Override
    public Set<User> getCommonFriends(int userId, int otherId) {
        User user = users.get(userId);
        User other = users.get(otherId);
        if (user == null || other == null) {
            log.warn("Пользователь с id {} или {} не найден", userId, otherId);
            throw new NotFoundException("Пользователь с таким id не найден");
        }
        Set<Integer> commonFriends = new HashSet<>(user.getFriends());
        commonFriends.retainAll(other.getFriends());
        log.info("Список общих друзей пользователей");
        return commonFriends.stream()
                .map(users::get)
                .collect(Collectors.toSet());
    }
}
