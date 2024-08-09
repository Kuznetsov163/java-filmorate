package ru.yandex.practicum.filmorate;


import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import java.time.LocalDate;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryFilmStorageTest {

    private final InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();

    @Test
    void testCreate() {
        Film film = Film.builder().
        name("Name")
                .description("Description")
                .releaseDate(LocalDate.parse("1896-10-10"))
                .duration(1000).build();
        Film createdFilm = filmStorage.createFilm(film);

        assertNotNull(createdFilm.getId());
        assertEquals("Name", createdFilm.getName());
        assertEquals("Description", createdFilm.getDescription());
        assertEquals(LocalDate.parse("1896-10-10"), createdFilm.getReleaseDate());
        assertEquals(1000, createdFilm.getDuration());
    }

    @Test
    void testUpdate() {
        Film film = Film.builder().
        name("Name")
                .description("Description")
                .releaseDate(LocalDate.parse("1896-10-10"))
                .duration(6).build();
        Film createdFilm = filmStorage.createFilm(film);
        film.setName("New Name");

        Film updatedFilm = filmStorage.updateFilm(film);

        assertEquals(createdFilm.getId(), updatedFilm.getId());
        assertEquals("New Name", updatedFilm.getName());
    }

    @Test
    void testGet() {
        Film film = Film.builder()
                .name("Name")
                .description("Description")
                .releaseDate(LocalDate.parse("1896-10-10"))
                .duration(6)
                .build();
        Film createdFilm = filmStorage.createFilm(film);

        Film foundFilm = filmStorage.getFilmId(createdFilm.getId()).orElseThrow();

        assertNotNull(foundFilm);
        assertEquals(createdFilm.getId(), foundFilm.getId());
        assertEquals(createdFilm.getName(), foundFilm.getName());
        assertEquals(createdFilm.getDescription(), foundFilm.getDescription());
        assertEquals(createdFilm.getReleaseDate(), foundFilm.getReleaseDate());
        assertEquals(createdFilm.getDuration(), foundFilm.getDuration());
    }



    @Test
    void testGetAll() {
        Film film = Film.builder().
        name("Name")
                .description("Description")
                .releaseDate(LocalDate.parse("1896-10-10"))
                .duration(6).build();
        Film film2 = Film.builder().
        name("Name2")
                .description("Description2")
                .releaseDate(LocalDate.parse("1896-10-14"))
                .duration(6).build();
        Film film3 = Film.builder()
                .name("Na")
                .description("Descri")
                .releaseDate(LocalDate.parse("1899-10-14"))
                .duration(888).build();
        filmStorage.createFilm(film);
        filmStorage.createFilm(film2);
        filmStorage.createFilm(film3);

        Collection<Film> allFilms = filmStorage.getAllFilm();

        assertEquals(3, allFilms.size());
        assertTrue(allFilms.contains(film));
        assertTrue(allFilms.contains(film2));
        assertTrue(allFilms.contains(film3));
    }

    @Test
    void testRemoveLike() {
        Film film = Film.builder()
                .name("Name")
        .description("Description")
        .releaseDate(LocalDate.parse("1896-10-10"))
                .duration(6).build();
        Film createdFilm = filmStorage.createFilm(film);
        filmStorage.addLike(createdFilm.getId(), 1);

        filmStorage.removeLike(createdFilm.getId(), 1);

        Film updatedFilm = filmStorage.getFilmId(createdFilm.getId()).orElseThrow();
        assertFalse(updatedFilm.getLikes().contains(1));
    }
}
