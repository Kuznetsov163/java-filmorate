package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import java.util.Set;

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
    public Film createFilm(@RequestBody Film film) {
        log.info("Получен запрос на создание фильма: {}", film);
        return filmService.create(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        log.info("Получен запрос на обновление фильма: {}", film);
        return filmService.update(film);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        log.info("Получен запрос на получение фильма по ID: {}", id);
        return filmService.get(id);
    }

    @GetMapping
    public Set<Film> getAllFilms() {
        log.info("Получен запрос на получение всех фильмов");
        return filmService.getAll();
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен запрос на добавление лайка: filmId={}, userId={}", id, userId);
        try {
            filmService.addLike(id, userId);
            log.info("Лайк успешно добавлен: filmId={}, userId={}", id, userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            log.error("Ошибка при добавлении лайка: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
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
        } catch (NotFoundException e) {
            log.error("Ошибка при удалении лайка: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Ошибка при удалении лайка: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/popular")
    public Set<Film> getPopularFilms(@RequestParam(required = false, defaultValue = "10") int count) {
        log.info("Получен запрос на получение списка популярных фильмов: count={}", count);
        return filmService.getTopFilms(count);
    }
}