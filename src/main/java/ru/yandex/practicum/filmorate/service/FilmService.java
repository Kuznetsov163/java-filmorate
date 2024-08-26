package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;


import java.util.*;


  @Service
  @Slf4j

public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

      public FilmService(@Autowired FilmStorage filmStorage, @Autowired UserStorage userStorage) {
          this.filmStorage = filmStorage;
          this.userStorage = userStorage;
      }

      public Optional<Film> findOne(Long filmId) {
          return filmStorage.getFilmId(filmId);
      }

    public  Film createFilm(Film film) {

        film = filmStorage.createFilm(film);
        return film;
    }

    public Film updateFilm(Film film) {
        Optional<Film> updatedFilm = findOne(film.getId());
        if (updatedFilm.isEmpty()) {
            throw new NotFoundException("Фильм не найден");
        } else {
            filmStorage.updateFilm(film);
        }
        return film;
    }


    public Collection<Film> getAllFilm() {
        log.info("Получен запрос на получение всех фильмов");
        return filmStorage.getAllFilm();
    }


    public void addLike(Long filmId, Long userId) {
        Optional<Film> film = filmStorage.getFilmId(filmId);
        if (film.isEmpty()) {
            throw new NotFoundException("Не найден фильм, которому присваивается лайк");
        } else if (userStorage.getUserId(userId).isEmpty()) {
            throw new NotFoundException("Не найден пользователь, который ставит лайк");
        } else if (filmId <= 0 || userId <= 0) {
            throw new ValidationException("Некорректный формат переданных параметров");
        } else {
            film.get().getLikes().add(userId);
            filmStorage.addLike(filmId, userId);
        }
    }

    public void removeLike(Long filmId, Long userId) {
        Optional<Film> film = filmStorage.getFilmId(filmId);
        if (film.isEmpty()) {
            throw new NotFoundException("Не найден фильм, у которого удаляется лайк");
        } else if (userStorage.getUserId(userId).isEmpty()) {
            throw new NotFoundException("Не найден пользователь, чей удаляется лайк");
        } else if (filmId <= 0 || userId <= 0) {
            throw new ValidationException("Некорректный формат переданных параметров");
        } else {
            film.get().getLikes().remove(userId);
            filmStorage.removeLike(filmId,userId);
        }
    }

      public List<Film> getTopFilms(long count) {
          Comparator<Film> comparator = Comparator.comparing(film -> film.getLikes().size(), Comparator.reverseOrder());
          if (count <= 0) {
              throw new ValidationException("Count должен быть больше 0");
          } else {
              return filmStorage.getTopFilms(count);
          }
      }
      }





