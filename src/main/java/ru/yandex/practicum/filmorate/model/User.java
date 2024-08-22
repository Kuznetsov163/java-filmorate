package ru.yandex.practicum.filmorate.model;
/*
Свойства model.User:
целочисленный идентификатор — id;
электронная почта — email;
логин пользователя — login;
имя для отображения — name;
дата рождения — birthday.
 */
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

 @Data
 @Slf4j
 @Builder(toBuilder = true)
   public class User {

     @NotNull(message = "id пользователя не может быть пустым")
     private long id;

     @NotNull(message = "Email не может быть пустым")
     private String email;

     @NotNull(message = "Логин не может быть пустым")
     private String login;

     @NotNull(message = "Имя пользователя не может быть пустым")
     private String name;

     @NotNull(message = "Дата рождения пользователя не может быть пустой")
     private LocalDate birthday;

     @NotNull(message = "Список друзей пользователя не может быть пустым")
     private Set<Long> friends = new HashSet<>();

}
