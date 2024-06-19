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
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

 @Data

public class User {

     private Integer id;


    private String email;


    private String login;

    private String name;


    private LocalDate birthday;

    private Set<Integer> friends = new HashSet<>();

}
