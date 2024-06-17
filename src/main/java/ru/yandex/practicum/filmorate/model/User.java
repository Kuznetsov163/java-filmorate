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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

 @Data

public class User {

     private Integer id;

    @NotBlank(message = "Электронная почта не может быть пустой!")
    @Email(message = "Некорректная электронная почта!")
    private String email;

    @NotBlank(message = "Логин не может быть пустым")
    private String login;

    private String name;

    @Past(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;

    private Set<Integer> friends = new HashSet<>();

}
