package ru.yandex.practicum.filmorate;


import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    private final UserService userController = new UserService(new InMemoryUserStorage());

    @Test
        // Пустой login
    void test_Create() {

        User user = User.builder()
                .email("@.ru")
                .name("Name")
                .login("")
                .birthday(LocalDate.parse("1900-01-01")).build();

        assertThrows(ValidationException.class, () -> userController.createUser(user));
    }

    @Test
        // Пустой email
    void test_Create2() {

        User user = User.builder()
                .email("")
                .name("Name")
                .login("g")
                .birthday(LocalDate.parse("1900-01-01")).build();

        assertThrows(ValidationException.class, () -> userController.createUser(user));
    }

    @Test
        //  Email без @
    void test_Create3() {
        User user = User.builder()
                .email(".ru")
                .name("Name")
                .login("g")
                .birthday(LocalDate.parse("1900-01-01")).build();

        assertThrows(ValidationException.class, () -> userController.createUser(user));
    }

    @Test
        // Содержание пробелов в login
    void test_Create4() {
        User user = User.builder()
                .email("@.ru")
                .name("Name")
                .login("g g")
                .birthday(LocalDate.parse("1900-01-01")).build();

        assertThrows(ValidationException.class, () -> userController.createUser(user));
    }

    @Test
        // Дата рождения в будующем
    void test_Create5() {
        User user = User.builder()
                .email("@.ru")
                .name("Name")
                .login("g")
                .birthday(LocalDate.parse("2222-01-01")).build();

        assertThrows(ValidationException.class, () -> userController.createUser(user));
    }

    @Test
        // Обновление пользователя
    void test_Update() {
        User user = User.builder()
                .email("@.ru")
                .name("Name")
                .login("gg")
                .birthday(LocalDate.parse("1900-01-01")).build();

        User createdUser = userController.createUser(user);
        user.setName("New Name");


        User updatedUser = userController.updateUser(user);

        assertEquals(createdUser.getId(), updatedUser.getId());
        assertEquals("New Name", updatedUser.getName());
    }
}