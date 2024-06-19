package ru.yandex.practicum.filmorate;


import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import java.time.LocalDate;
import java.util.Set;

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

        Film foundFilm = filmStorage.get(createdFilm.getId());

        assertNotNull(foundFilm);
        assertEquals(createdFilm.getId(), foundFilm.getId());
        assertEquals(createdFilm.getName(), foundFilm.getName());
        assertEquals(createdFilm.getDescription(), foundFilm.getDescription());
        assertEquals(createdFilm.getReleaseDate(), foundFilm.getReleaseDate());
        assertEquals(createdFilm.getDuration(), foundFilm.getDuration());

    }


    @Test
    void testGetNotFound() {
        assertThrows(NotFoundException.class, () -> filmStorage.get(100));
    }

    @Test
    void testGetAll() {
        Film film = new Film();
        film.setName("Name");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.parse("1896-10-10"));
        film.setDuration(6);
        Film film2 = new Film();
        film2.setName("Name2");
        film2.setDescription("Description2");
        film2.setReleaseDate(LocalDate.parse("1896-10-14"));
        film2.setDuration(6);
        Film film3 = new Film();
        film3.setName("Na");
        film3.setDescription("Descri");
        film3.setReleaseDate(LocalDate.parse("1899-10-14"));
        film3.setDuration(888);
        filmStorage.create(film);
        filmStorage.create(film2);
        filmStorage.create(film3);

        Set<Film> allFilms = filmStorage.getAll();

        assertEquals(3, allFilms.size());
        assertTrue(allFilms.contains(film));
        assertTrue(allFilms.contains(film2));
        assertTrue(allFilms.contains(film3));
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

        Film updatedFilm = filmStorage.get(createdFilm.getId());
        assertFalse(updatedFilm.getLikes().contains(1));
    }


}
