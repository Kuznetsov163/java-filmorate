package ru.yandex.practicum.filmorate.model;
/*
Свойства model.User:
целочисленный идентификатор — id;
электронная почта — email;
логин пользователя — login;
имя для отображения — name;
дата рождения — birthday.
 */

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

 @Data
 @Slf4j
 @Builder(toBuilder = true)
   public class User {

     @EqualsAndHashCode.Exclude
     private Long id;
     private String email;
     private String login;
     private String name;
     private LocalDate birthday;
     private Set<Long> friends = new HashSet<>();

}
