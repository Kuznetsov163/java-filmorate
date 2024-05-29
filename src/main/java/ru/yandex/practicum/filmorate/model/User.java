package ru.yandex.practicum.filmorate.model;
/*
Свойства model.User:
целочисленный идентификатор — id;
электронная почта — email;
логин пользователя — login;
имя для отображения — name;
дата рождения — birthday.
 */

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

 @Data

 @Getter

 @Setter
public class User {

    Integer id;

    @NotBlank(message = "Электронная почта не может быть пустой!")
    @Email(message = "Некорректная электронная почта!")
    String email;

    @NotBlank(message = "Логин не может быть пустым")
     String login;

    String name;

    @Past(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;


}
