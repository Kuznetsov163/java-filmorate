package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import ru.yandex.practicum.filmorate.model.*;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private final Map<Integer, Set<Integer>> friends = new HashMap<>();
    private int nextId = 1;

    @Override
    public User create(User user) {
        log.info("Создание нового пользователя: {}", user);
        if (users.values().stream().anyMatch(u -> u.getLogin().equals(user.getLogin()))) {  // проверка есть ли такой логин
            log.warn("Такой пользователь {} уже существует", user.getLogin());
            throw new RuntimeException("Такой пользователь уже есть");
        }
        if (users.values().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) { // проверка есть ли такой Email
            log.warn("Такой email {} уже существует", user.getEmail());
            throw new RuntimeException("Такой email уже есть");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        user.setId(nextId++);
        users.put(user.getId(), user);
        friends.put(user.getId(), new HashSet<>());
        return user;
    }

    @Override
    public User update(User user) {
        log.info("Обновление пользователя: {}", user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> get(int id) {
        log.info("Получение пользователя по ID: {}", id);
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> getAll() {
        log.info("Получение всех пользователей");
        return new ArrayList<>(users.values());
    }

    @Override
    public void addFriend(int userId, int friendId) {
        log.info("Добавление друга {} к пользователю {}", friendId, userId);
        friends.get(userId).add(friendId);
        friends.get(friendId).add(userId);
    }

    @Override
    public void removeFriend(int userId, int friendId) {
        log.info("Удаление друга {} от пользователя {}", friendId, userId);
        friends.get(userId).remove(friendId);
        friends.get(friendId).remove(userId);
    }

    @Override
    public List<User> getFriends(int userId) {
        log.info("Получение списка друзей пользователя {}", userId);
        return friends.get(userId).stream()
                .map(users::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getCommonFriends(int userId, int otherId) {
        log.info("Получение списка общих друзей пользователей {} и {}", userId, otherId);
        Set<Integer> userFriends = friends.get(userId);
        Set<Integer> otherFriends = friends.get(otherId);
        return userFriends.stream()
                .filter(otherFriends::contains)
                .map(users::get)
                .collect(Collectors.toList());
    }
}
