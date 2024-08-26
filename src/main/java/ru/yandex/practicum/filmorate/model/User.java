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
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

 @Data
   public class User {

     @EqualsAndHashCode.Exclude
     private Long id;
     private String email;
     private String login;
     private String name;
     private LocalDate birthday;
     private Set<Long> friends = new HashSet<>();

}
