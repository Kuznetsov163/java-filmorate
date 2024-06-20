package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FilmServiceTest {
    private final FilmService filmService = new FilmService(new InMemoryFilmStorage());
    private final UserService userService = new UserService(new InMemoryUserStorage());


 // Проверяю, что в топ-2 входит фильм 1 (2 лайка) и фильм 2 (1 лайк)
    @Test
    void testGetTopFilms() {
        Film film1 = new Film();
        film1.setId(1);
        film1.setName("Name");
        film1.setDescription("Description");
        film1.setReleaseDate(LocalDate.parse("1896-10-10"));
        film1.setDuration(100);

        Film film2 = new Film();
        film2.setId(2);
        film2.setName("Name1");
        film2.setDescription("Description1");
        film2.setReleaseDate(LocalDate.parse("1899-10-10"));
        film2.setDuration(120);
        film1 = filmService.create(film1);
        film2 = filmService.create(film2);

        User user1 = new User();
        user1.setId(1);
        user1.setEmail("@.ruuu");
        user1.setName("Name2");
        user1.setLogin("gghh");
        user1.setBirthday(LocalDate.parse("1988-01-01"));


        User user2 = new User();
        user2.setId(2);
        user2.setEmail("@.ru");
        user2.setName("Name");
        user2.setLogin("gg");
        user2.setBirthday(LocalDate.parse("1900-01-01"));

        user1 = userService.create(user1);
        user2 = userService.create(user2);

        filmService.addLike(film1.getId(), user1.getId());
        filmService.addLike(film1.getId(), user2.getId());

        filmService.addLike(film2.getId(), user1.getId());

        Set<Film> topFilms = filmService.getTopFilms(2);

        assertEquals(2, topFilms.size());
        assertTrue(topFilms.contains(film1));
        assertTrue(topFilms.contains(film2));
    }
}
