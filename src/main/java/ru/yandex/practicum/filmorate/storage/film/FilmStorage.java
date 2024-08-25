package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.Optional;

@Component
public interface FilmStorage {

    Film createFilm(Film film);

    Film updateFilm(Film film);

    Optional<Film> getFilmId(long id);

    Collection<Film> getAllFilm();

    void addLike(long filmId, long userId);

    void removeLike(long filmId, long userId);

    Collection<Film> getTopFilms(long count);
}
