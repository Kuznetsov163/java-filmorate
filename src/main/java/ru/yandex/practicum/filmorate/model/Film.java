package ru.yandex.practicum.filmorate.model;
/*
У model.Film должны быть следующие свойства:
целочисленный идентификатор — id;
название — name;
описание — description;
дата релиза — releaseDate;
продолжительность фильма — duration.
 */
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


  @Data

  public class Film {
    private Integer id;

  @NotBlank(message = "Название фильма не может быть пустым")
  private String name;

  @Size(max = 200, message = "Максимальная длина описания — 200 символов")
  private String description;

  @NotNull(message = "Дата релиза не может быть пустой")

  private LocalDate releaseDate;

  @Positive(message = "Продолжительность фильма должна быть положительным числом")
  private int duration;

}
