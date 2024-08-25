package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private long id = 1;

    @Override
    public User createUser(User user) {
        user.setId(id++);
        users.put(user.getId(), user);
        return user;

    }

    @Override
    public User updateUser(User user) {
        if (user.getId() == 0) {
            throw new NotFoundException("Id фильма должен быть указан");
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Collection<User> getAllUser() {
        if (users.isEmpty()) {
            log.info("Список пользователей пуст");
            throw new NotFoundException("Список пользователей пуст");
        }
        return users.values();
    }

    @Override
    public Optional<User> getUserId(long id) {
        return Optional.ofNullable(users.get(id));
    }



    @Override
    public void addFriend(long userId, long friendId) {
        User user = users.get(userId);
        Set<Long> friendSet;
        if (user.getFriends() == null) {
            friendSet = new HashSet<>();
        } else {
            friendSet = new HashSet<>(user.getFriends());
        }
        friendSet.add(friendId);
        user.setFriends(friendSet);
    }


    @Override
    public void removeFriend(long userId, long friendId) {
        User user = users.get(userId);
        User friend = users.get(friendId);
        if (user.getFriends() == null) {
            return;
        }
        if (user == null || friend == null) {
            log.warn("Пользователь с id {} не найден", userId);
            throw new NotFoundException("Пользователь с таким id не найден");
        }
        Set<Long> friendSet1;
        Set<Long> friendSet2;
        if (user.getFriends() == null || friend.getFriends() == null) {
            throw new NotFoundException("Список друзей пользователя не найден");
        } else {
            friendSet1 = user.getFriends();
            friendSet2 = friend.getFriends();
        }
        friendSet1.remove(friendId);
        user.setFriends(friendSet1);
        friendSet2.remove(userId);
        friend.setFriends(friendSet2);
        log.info("Пользователь {} удалил из друзей пользователя {}", userId, friendId);
    }

    @Override
    public Collection<User> getCommonFriends(long id, long otherId) {
        Set<Long> friends = users.get(otherId).getFriends();
        return users.get(id).getFriends().stream()
                .filter(friends::contains)
                .map(users::get)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<User> getFriends(long id) {

        if (users.get(id).getFriends() == null) {
             return new HashSet<>();
         }
        return users.get(id).getFriends().stream()
                .map(users::get).collect(Collectors.toSet());
    }
}
