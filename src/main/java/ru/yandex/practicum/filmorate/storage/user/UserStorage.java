package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public interface UserStorage {

    User createUser(User user);

    User updateUser(User user);

    Optional<User> getUserId(Long userId);

    Collection<User> getAllUser();

    void addFriend(Long oneUser, Long twoId);

    void removeFriend(Long userOneId, Long userTwoId);

    Collection<User> getFriends(Long id);

    Collection<User> getCommonFriends(Long userOneId, Long userTwoId);

}
