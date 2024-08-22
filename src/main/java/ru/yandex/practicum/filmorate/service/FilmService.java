package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import java.time.LocalDate;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;


import java.util.*;

  @RequiredArgsConstructor
  @Service
  @Slf4j

public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;




    public  Film createFilm(Film film) {
        log.info("Получен запрос на создание фильма: {}", film);
        validateFilm(film);
        film = filmStorage.createFilm(film);
        log.info("Фильм успешно добавлен {}", film);
        return film;
    }

    public Film updateFilm(Film film) {
        if (film.getId() == 0) {
            throw new ValidationException("Id фильма должен быть указан");
        }
        validateFilm(film);
        getFilmId(film.getId());
        log.info("Получен запрос на обновление фильма: {}", film);
        Film filUp = filmStorage.getFilmId(film.getId()).get();
        filUp = filUp.toBuilder()
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration())
                .build();
        filmStorage.updateFilm(filUp);
        log.info("Обновлен фильм {}", film);
        return film;
    }


    public Collection<Film> getAllFilm() {
        log.info("Получен запрос на получение всех фильмов");
        return filmStorage.getAllFilm();
    }


    public void addLike(long filmId, long userId) {
        getFilmId(filmId);
        getUserId(userId);
        log.info("Получен запрос на добавление лайка: {}, {}", filmId, userId);
        filmStorage.addLike(filmId, userId);
        log.info("Лайк успешно добавлен: {}, {}", userId, filmId);
    }

    public void removeLike(long filmId, long userId) {
        getFilmId(filmId);
        getUserId(userId);
        log.info("Получен запрос на удаление лайка: filmId={}, userId={}", filmId, userId);
        filmStorage.removeLike(filmId, userId);
        log.info("Лайк успешно удален: filmId={}, userId={}", filmId, userId);
    }

      public Collection<Film> getTopFilms(long count) {
          log.info("Получен запрос на получение списка популярных фильмов");
          return filmStorage.getTopFilms(count);
      }

      private void getFilmId(long filmId) {
          if (filmStorage.getFilmId(filmId).isEmpty()) {
              log.warn("Фильм с id {} не найден", filmId);
              throw new NotFoundException("Фильм с id  " + filmId + " не найден");
          }
      }

      private void getUserId(long id) {
          if (userStorage.getUserId(id).isEmpty()) {
              log.warn("Пользователь с id {} не найден", id);
              throw new NotFoundException("Фильм с id " + id + " не найден");
          }
      }

    private void validateFilm(Film film) {
        if (film.getName().isBlank()) {
            log.warn("Название фильма не может быть пустым");
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.warn("Описание фильма не может превышать 200 символов");
            throw new ValidationException("Описание фильма не может превышать 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Дата релиза не может быть раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            log.warn("Продолжительность фильма должна быть положительным числом");
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
    }
}

