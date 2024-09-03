package ru.yandex.practicum.filmorate.model;
/*
У model. Film должны быть следующие свойства:
целочисленный идентификатор — id;
название — name;
описание — description;
дата релиза — releaseDate;
продолжительность фильма — duration.
 */
import lombok.Data;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import jakarta.validation.constraints.*;

   @Data
   public class Film {


    private Long id;

    @NotBlank(message = "Название фильма не может быть пустым")
    private String name;

    @Size(max = 200, message = "Длина описания не может быть более 200 символов")
    private String description;

    private LocalDate releaseDate;

    @Min(value = 0, message = "Продолжительность фильма не может быть отрицательным значением.")
    private Long duration;

    private Set<Long> likes = new HashSet<>();

    private TreeSet<Genre> genres = new TreeSet<>();

    private MPA mpa;

    public static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);


   }
