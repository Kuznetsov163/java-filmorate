package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.*;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;

@RestController
@Slf4j
@RequestMapping("/films")
@Qualifier("FilmDbStorage")
public class FilmController {

    private final FilmService filmService;
    private final MPAService mpaService;
    private final GenreService genreService;

    public FilmController(@Autowired FilmService filmService, MPAService mpaService, GenreService genreService) {

        this.filmService = filmService;
        this.mpaService = mpaService;
        this.genreService = genreService;
    }


    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.removeLike(id, userId);
    }


    @GetMapping("/popular")
    public List<Film> getTopFilms(@PathVariable("count") @RequestParam(defaultValue = "10") long count) {
        return filmService.getTopFilms(count);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidation(final ValidationException e) {
        log.warn("Ошибка валидации: {}", e.getMessage(), e);
        return Map.of("error", "Произошла ошибка валидации одного из параметров: " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(final NotFoundException e) {
        log.warn("Ресурс не найден: {}", e.getMessage(), e);
        return Map.of("error", "Не найден переданный параметр.");
    }

    @PostMapping
    public Film createFilm(@RequestBody @Valid Film film) {
        if (mpaService.findOne(film.getMpa().getId()).isEmpty()) {
            throw new ValidationException("Не найден MPA с рейтингом" + film.getMpa().getId());
        } else if (!checkGenres(film)) {
            throw new ValidationException("Не найден один из переданных жанров");
        }
        return filmService.createFilm(film);
    }

    @GetMapping("/{id}")
    public Optional<Film> findOne(@PathVariable Long id) {
        return filmService.findOne(id);
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) {
        return filmService.updateFilm(film);
    }

    @GetMapping
    public Collection<Film> getAllFilm() {
        return filmService.getAllFilm();
    }

    private boolean checkGenres(Film film) {   // исправлено
        TreeSet<Genre> genres = film.getGenres();
        if (genres == null) {
            return true;
        }
        Collection<Genre> existingGenres = genreService.findAll();
        Set<Long> existingGenreIds = existingGenres.stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());

        for (Genre genre : genres) {
            if (!existingGenreIds.contains(genre.getId())) {
                return false;
            }
        }
        return true;
    }
}