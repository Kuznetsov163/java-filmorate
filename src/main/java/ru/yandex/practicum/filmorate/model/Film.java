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
import java.util.HashSet;
import java.util.Set;

import java.time.LocalDate;


  @Data

  public class Film {

    private int id;

  private String name;

  private String description;


  private LocalDate releaseDate;

  private int duration;

  private Set<Integer> likes = new HashSet<>();

}
