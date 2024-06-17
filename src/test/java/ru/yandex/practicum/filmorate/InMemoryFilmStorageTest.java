package ru.yandex.practicum.filmorate;


import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryFilmStorageTest {

    private final InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();

    @Test
    void testCreate() {
        Film film = new Film();
        film.setName("Name");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.parse("1896-10-10"));
        film.setDuration(1000);
        Film createdFilm = filmStorage.create(film);

        assertNotNull(createdFilm.getId());
        assertEquals("Name", createdFilm.getName());
        assertEquals("Description", createdFilm.getDescription());
        assertEquals(LocalDate.parse("1896-10-10"), createdFilm.getReleaseDate());
        assertEquals(1000, createdFilm.getDuration());
    }

    @Test
    void testUpdate() {
        Film film = new Film();
        film.setName("Name");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.parse("1896-10-10"));
        film.setDuration(6);
        Film createdFilm = filmStorage.create(film);
        film.setName("New Name");

        Film updatedFilm = filmStorage.update(film);

        assertEquals(createdFilm.getId(), updatedFilm.getId());
        assertEquals("New Name", updatedFilm.getName());
    }

    @Test
    void testGet() {
        Film film = new Film();
        film.setName("Name");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.parse("1896-10-10"));
        film.setDuration(6);
        Film createdFilm = filmStorage.create(film);

        Optional<Film> foundFilm = filmStorage.get(createdFilm.getId());

        assertTrue(foundFilm.isPresent());
        assertEquals(createdFilm.getId(), foundFilm.get().getId());
    }

    @Test
    void testGetNotFound() {
        Optional<Film> foundFilm = filmStorage.get(100);

        assertFalse(foundFilm.isPresent());
    }

    @Test
    void testGetAll() {
        Film film = new Film();
        film.setName("Name");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.parse("1896-10-10"));
        film.setDuration(6);
        Film film2 = new Film();
        film.setName("Name2");
        film.setDescription("Description2");
        film.setReleaseDate(LocalDate.parse("1896-10-14"));
        film.setDuration(6);
        filmStorage.create(film);
        filmStorage.create(film2);

        List<Film> allFilms = filmStorage.getAll();

        assertEquals(2, allFilms.size());
        assertTrue(allFilms.contains(film));
        assertTrue(allFilms.contains(film2));
    }

    @Test
    void testRemoveLike() {
        Film film = new Film();
        film.setName("Name");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.parse("1896-10-10"));
        film.setDuration(6);
        Film createdFilm = filmStorage.create(film);
        filmStorage.addLike(createdFilm.getId(), 1);

        filmStorage.removeLike(createdFilm.getId(), 1);

        assertFalse(filmStorage.get(createdFilm.getId()).get().getLikes().contains(1));
    }
}
