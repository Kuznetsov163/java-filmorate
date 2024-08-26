package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;

@RestController
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

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        if (mpaService.findOne(film.getMpa().getId()).isEmpty()) {
            throw new ValidationException("Не найден MPA с рейтингом" + film.getMpa().getId());
        }
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.removeLike(id, userId);
    }

    @GetMapping
    public Collection<Film> getAllFilm() {
        return filmService.getAllFilm();
    }

    @GetMapping("/{id}")
    public Optional<Film> findOne(@PathVariable Long id) {
        return filmService.findOne(id);
    }

    @GetMapping("/popular")
    public List<Film> getTopFilms(@RequestParam(defaultValue = "10") long count) {
        return filmService.getTopFilms(count);
    }
}