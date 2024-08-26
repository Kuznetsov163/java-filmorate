package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.util.Collection;
import java.util.Optional;


@Service
public class UserService {

    private final UserStorage userStorage;

    public UserService(@Autowired UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addFriend(Long userOneId, Long userTwoId) {
        Optional<User> one = userStorage.getUserId(userOneId);
        Optional<User> two = userStorage.getUserId(userTwoId);
        if (one.isEmpty() || two.isEmpty()) {
            throw new NotFoundException("Нельзя добавить в друзья несуществующих пользователей");
        } else if (userOneId <= 0 || userTwoId <= 0) {
            throw new ValidationException(userOneId + " " + userTwoId);
        } else {
            userStorage.addFriend(userOneId,userTwoId);
            return one.get();
        }
    }

    public void removeFriend(Long userOneId, Long userTwoId) {
        Optional<User> one = userStorage.getUserId(userOneId);
        Optional<User> two = userStorage.getUserId(userTwoId);
        if (one.isEmpty() || two.isEmpty()) {
            throw new NotFoundException("Нельзя удалить из друзей несуществующих пользователей");
        } else if (userOneId <= 0 || userTwoId <= 0) {
            throw new ValidationException(userOneId + " " + userTwoId);
        } else {
            one.get().getFriends().remove(userTwoId);
            two.get().getFriends().remove(userOneId);
            userStorage.removeFriend(userOneId, userTwoId);
        }
    }

    public Collection<User> getFriends(Long id) {
        Optional<User> searchedUser = userStorage.getUserId(id);
        if (searchedUser.isEmpty()) {
            throw new NotFoundException("Запрошен список друзей у несуществующего пользователя");
        }
        return userStorage.getFriends(id);
    }

    public Collection<User> getCommonFriends(Long userOneId, Long userTwoId) {
        Optional<User> one = userStorage.getUserId(userOneId);
        Optional<User> two = userStorage.getUserId(userTwoId);
        if (one.isEmpty() || two.isEmpty()) {
            throw new NotFoundException("Нельзя найти список общих друзей у несуществующих пользователей");
        } else if (userOneId <= 0 || userTwoId <= 0) {
            throw new ValidationException(userOneId + " " + userTwoId);
        } else {
            return userStorage.getCommonFriends(userOneId,userTwoId);
        }
    }

    public Optional<User> findOne(Long userId) {
        return userStorage.getUserId(userId);
    }

    public User updateUser(User user) {
        Optional<User> updatedUser = findOne(user.getId());
        if (updatedUser.isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        } else {
            userStorage.updateUser(user);
        }
        return user;
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public Collection<User> getAllUser() {
        return userStorage.getAllUser();
    }


}
