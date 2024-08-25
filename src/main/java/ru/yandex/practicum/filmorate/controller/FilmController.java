package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.*;
import java.util.Collection;



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
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable long id, @PathVariable long userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable long id, @PathVariable long userId) {
        filmService.removeLike(id, userId);
    }

    @GetMapping
    public Collection<Film> getAllFilm() {
        return filmService.getAllFilm();
    }

    @GetMapping("/popular")
    public Collection<Film> getTopFilms(@RequestParam(defaultValue = "10") long count) {
        return filmService.getTopFilms(count);
    }

}