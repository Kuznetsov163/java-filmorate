package ru.yandex.practicum.filmorate;


import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    private final UserService userController = new UserService(new InMemoryUserStorage());

    @Test  // Пустой login
    void test_Create() {

        User user = new User();
        user.setEmail("@.ru");
        user.setName("Name");
        user.setLogin("");
        user.setBirthday(LocalDate.parse("1900-01-01"));

        assertThrows(ValidationException.class, () -> userController.create(user));
    }

    @Test // Пустой email
    void test_Create2() {

        User user = new User();
        user.setEmail("");
        user.setName("Name");
        user.setLogin("g");
        user.setBirthday(LocalDate.parse("1900-01-01"));

        assertThrows(ValidationException.class, () -> userController.create(user));
    }

    @Test  //  Email без @
    void test_Create3() {
        User user = new User();
        user.setEmail(".ru");
        user.setName("Name");
        user.setLogin("g");
        user.setBirthday(LocalDate.parse("1900-01-01"));

        assertThrows(ValidationException.class, () -> userController.create(user));
    }

    @Test  // Содержание пробелов в login
    void test_Create4() {
        User user = new User();
        user.setEmail("@.ru");
        user.setName("Name");
        user.setLogin("g g");
        user.setBirthday(LocalDate.parse("1900-01-01"));

        assertThrows(ValidationException.class, () -> userController.create(user));
    }

    @Test  // Дата рождения в будующем
    void test_Create5() {
        User user = new User();
        user.setEmail("@.ru");
        user.setName("Name");
        user.setLogin("g");
        user.setBirthday(LocalDate.parse("2222-01-01"));

        assertThrows(ValidationException.class, () -> userController.create(user));
    }

    @Test // Обновление пользователя
    void test_Update() {
        User user = new User();
        user.setEmail("@.ru");
        user.setName("Name");
        user.setLogin("gg");
        user.setBirthday(LocalDate.parse("1900-01-01"));

        User createdUser = userController.create(user);
        user.setName("New Name");


        User updatedUser = userController.update(user);

        assertEquals(createdUser.getId(), updatedUser.getId());
        assertEquals("New Name", updatedUser.getName());
    }

    @Test // Получение пользователя по ID
    void test_Get() {
        User user = new User();
        user.setEmail("@.ru");
        user.setName("Name");
        user.setLogin("gg");
        user.setBirthday(LocalDate.parse("1900-01-01"));
        User createdUser = userController.create(user);

        User foundUser = userController.get(createdUser.getId());

        assertNotNull(foundUser);
        assertEquals(createdUser.getId(), foundUser.getId());
    }
}