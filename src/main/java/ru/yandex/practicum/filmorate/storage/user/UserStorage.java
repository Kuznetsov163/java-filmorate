package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public interface UserStorage {

    User createUser(User user);

    User updateUser(User user);

    Optional<User> getUserId(long id);

    Collection<User> getAllUser();

    void addFriend(long userId, long friendId);

    void removeFriend(long userId, long friendId);

    Collection<User> getFriends(long id);

    Collection<User> getCommonFriends(long id, long otherId);

}
