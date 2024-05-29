package ru.yandex.practicum.filmorate;


import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.controller.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {
    private final FilmController filmController = new FilmController();

    @Test
    void test_Create() {


            Film film = new Film();
            film.setName("");
            film.setDescription("Description");
            film.setDuration(1000);
            film.setReleaseDate(LocalDate.parse("1896-10-10"));

        assertThrows(ValidationException.class, () -> filmController.createFilm(film));

    }

    @Test
    void test_Create_WithInvalidDate() {

        Film film = new Film();
        film.setName("Name");
        film.setDescription("Description");
        film.setDuration(1000);
        film.setReleaseDate(LocalDate.parse("1890-10-10"));

        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }
}