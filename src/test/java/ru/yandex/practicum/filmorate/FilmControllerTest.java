package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {
    private final FilmService filmController = new FilmService(new InMemoryFilmStorage(), new InMemoryUserStorage());

    @Test // пустое название
    void test_Create() {
        Film film = Film.builder()
                .name("")
                .description("Description")
                .duration(1000L)
                .releaseDate(LocalDate.parse("1896-10-10")).build();

        assertThrows(ValidationException.class, () -> filmController.createFilm(film));

    }

    @Test // раньше 28 декабря 1895
    void test_Create_2() {
        Film film = Film.builder()
                .name("Name")
                .description("Description")
                .duration(1000L)
                .releaseDate(LocalDate.parse("1890-10-10")).build();

        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }

    @Test // Продолжительность фильма 0
    void test_Create_3() {
        Film film = Film.builder()
                .name("Name")
                .description("Description")
                .duration(0L)
                .releaseDate(LocalDate.parse("1896-10-10")).build();


        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }

    @Test // Продолжительность фильма отрицательная
    void test_Create_4() {
        Film film =  Film.builder()
                .name("Name")
                .description("Description")
                .duration(-1000L)
                .releaseDate(LocalDate.parse("1896-10-10")).build();

        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }

    @Test // Больше 200 символов
    void test_Create_5() {
        Film film = Film.builder()
                .name("Name")
                .description("D".repeat(201))
                .duration(1000L)
                .releaseDate(LocalDate.parse("1896-10-10")).build();


        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }
}