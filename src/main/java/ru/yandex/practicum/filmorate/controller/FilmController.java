package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public ResponseEntity<Film> createFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос на создание фильма: {}", film);
        try {
            Film createdFilm = filmService.create(film);
            log.info("Фильм успешно создан: {}", createdFilm);
            return new ResponseEntity<>(createdFilm, HttpStatus.CREATED);
        } catch (ValidationException e) {
            log.warn("Ошибка валидации при создании фильма: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            log.error("Ошибка при создании фильма: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable int id) {
        log.info("Получен запрос на получение фильма по ID: {}", id);
        try {
            Film film = filmService.get(id);
            log.info("Фильм успешно получен: {}", film);
            return new ResponseEntity<>(film, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Ошибка при получении фильма: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен запрос на добавление лайка: filmId={}, userId={}", id, userId);
        try {
            filmService.addLike(id, userId);
            log.info("Лайк успешно добавлен: filmId={}, userId={}", id, userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Ошибка при добавлении лайка: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> removeLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен запрос на удаление лайка: filmId={}, userId={}", id, userId);
        try {
            filmService.removeLike(id, userId);
            log.info("Лайк успешно удален: filmId={}, userId={}", id, userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Ошибка при удалении лайка: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Film>> getPopularFilms(@RequestParam(required = false, defaultValue = "10") int count) {
        log.info("Получен запрос на получение списка популярных фильмов: count={}", count);
        try {
            List<Film> popularFilms = filmService.getTopFilms(count);
            log.info("Список популярных фильмов успешно получен: count={}", count);
            return new ResponseEntity<>(popularFilms, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Ошибка при получении списка популярных фильмов: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}