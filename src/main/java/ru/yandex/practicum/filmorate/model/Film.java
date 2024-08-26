package ru.yandex.practicum.filmorate.model;
/*
У model. Film должны быть следующие свойства:
целочисленный идентификатор — id;
название — name;
описание — description;
дата релиза — releaseDate;
продолжительность фильма — duration.
 */
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


   @Data
   @Slf4j
   @Builder(toBuilder = true)
   public class Film {


    private Long id;

    @NotNull(message = "Название фильма не может быть пустым")
    private String name;

    private String description;

    private LocalDate releaseDate;

    private Long duration;

    private Set<Long> likes = new HashSet<>();

    private TreeSet<Genre> genres = new TreeSet<>();

    private MPA mpa;

}
