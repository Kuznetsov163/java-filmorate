package ru.yandex.practicum.filmorate.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import java.time.LocalDate;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.List;

@Service
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film create(Film film) {
        validateFilm(film);
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        validateFilm(film);
        return filmStorage.update(film);
    }

    public Film get(int id) {
        return filmStorage.get(id).orElseThrow(() -> new NotFoundException("Фильм с таким id не найден"));
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public void addLike(int filmId, int userId) {
        if (!filmStorage.get(filmId).isPresent()) {
            throw new NotFoundException("Фильм с таким id не найден");
        }
        filmStorage.addLike(filmId, userId);
    }

    public void removeLike(int filmId, int userId) {
        if (!filmStorage.get(filmId).isPresent()) {
            throw new NotFoundException("Фильм с таким id не найден");
        }
        filmStorage.removeLike(filmId, userId);
    }

    public List<Film> getTopFilms(int count) {
        return filmStorage.getTopFilms(count);
    }

    private void validateFilm(Film film) {
        if (film.getName().isEmpty()) {
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание фильма не может превышать 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
    }
}
