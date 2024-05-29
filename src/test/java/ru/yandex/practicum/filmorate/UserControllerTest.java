package ru.yandex.practicum.filmorate;


import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.controller.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

 public class UserControllerTest {

    private final UserController userController = new UserController();

    @Test
    void test_Create() {

        User user = new User();
        user.setEmail("@.ru");
        user.setName("Name");
        user.setLogin("");
        user.setBirthday(LocalDate.parse("1900-01-01"));

        assertThrows(ValidationException.class, () -> userController.createUser(user));
  }
    @Test
    void test_Create2() {

        User user = new User();
        user.setEmail("");
        user.setName("Name");
        user.setLogin("g");
        user.setBirthday(LocalDate.parse("1900-01-01"));

        assertThrows(ValidationException.class, () -> userController.createUser(user));
    }
}