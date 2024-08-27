package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public interface FilmStorage {

     Film createFilm(Film film);   // исправленно

     Film updateFilm(Film film);

     Optional<Film> getFilmId(Long filmId);

    Collection<Film> getAllFilm();

    void addLike(Long filmId, Long userId);

    void removeLike(Long filmId, Long userId);

    List<Film> getTopFilms(long count);
}
