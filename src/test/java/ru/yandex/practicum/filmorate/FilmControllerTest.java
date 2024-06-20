package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.service.FilmService;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {
    private final FilmService filmController = new FilmService(new InMemoryFilmStorage());

    @Test // пустое название
    void test_Create() {


        Film film = new Film();
        film.setId(1);
        film.setName("");
        film.setDescription("Description");
        film.setDuration(1000);
        film.setReleaseDate(LocalDate.parse("1896-10-10"));

        assertThrows(ValidationException.class, () -> filmController.create(film));

    }

    @Test // раньше 28 декабря 1895
    void test_Create_2() {

        Film film = new Film();
        film.setId(1);
        film.setName("Name");
        film.setDescription("Description");
        film.setDuration(1000);
        film.setReleaseDate(LocalDate.parse("1890-10-10"));

        assertThrows(ValidationException.class, () -> filmController.create(film));
    }

    @Test // Продолжительность фильма 0
    void test_Create_3() {
        Film film = new Film();
        film.setId(1);
        film.setName("Name");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.parse("1896-10-10"));
        film.setDuration(0);

        assertThrows(ValidationException.class, () -> filmController.create(film));
    }

    @Test // Продолжительность фильма отрицательная
    void test_Create_4() {
        Film film = new Film();
        film.setId(1);
        film.setName("Name");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.parse("1896-10-10"));
        film.setDuration(-1000);

        assertThrows(ValidationException.class, () -> filmController.create(film));
    }

    @Test // Больше 200 символов
    void test_Create_5() {
        Film film = new Film();
        film.setId(1);
        film.setName("Name");
        film.setDescription("D".repeat(201));
        film.setReleaseDate(LocalDate.parse("1896-10-10"));
        film.setDuration(1000);

        assertThrows(ValidationException.class, () -> filmController.create(film));
    }
    @Test
    void test_Create9() {
        Film film = new Film();
        film.setId(0);
        film.setName("Name");
        film.setDescription("D");
        film.setReleaseDate(LocalDate.parse("1896-10-10"));
        film.setDuration(1000);

        assertThrows(ValidationException.class, () -> filmController.create(film));
    }
}