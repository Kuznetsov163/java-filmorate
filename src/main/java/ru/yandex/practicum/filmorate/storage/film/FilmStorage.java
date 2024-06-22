package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import java.util.Set;

public interface FilmStorage {

    Film create(Film film);

    Film update(Film film);

    Film get(int id);

    Set<Film> getAll();

    void addLike(int filmId, int userId);

    void removeLike(int filmId, int userId);

    Set<Film> getTopFilms(int count);
}
